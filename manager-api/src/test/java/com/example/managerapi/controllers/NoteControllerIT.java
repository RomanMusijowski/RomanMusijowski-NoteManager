package com.example.managerapi.controllers;

import com.example.managerapi.ManagerApiApplication;
import com.example.managerapi.domain.Note;
import com.example.managerapi.repos.NoteRepo;
import com.example.managerapi.util.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
@RunWith(SpringRunner.class)
@ActiveProfiles("integration-test")
@SpringBootTest(
        classes = ManagerApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-integration-test.yaml")
class NoteControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private NoteRepo noteRepo;

    private Long versionOne = 1L;
    private Long versionTwo = 2L;
    private Note note;
    private final String title = "title";
    private final String title2 = "title2";
    private final String content = "content";
    private final String content2 = "content2";

    @AfterEach
    void tearDown() {
        noteRepo.deleteAll();
        log.info("-- tearDown");
    }

    @Test
    void whenGetNoteById_thenStatus200() throws Exception {
        createNotes();

        mvc.perform(get("/api/note/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    void whenGetNoteById_thenStatus400() throws Exception {

        mvc.perform(get("/api/note/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenGetAllNotes_thenStatus200() throws Exception {
        createNotes();

        mvc.perform(get("/api/note")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").value(hasSize(2)));
    }

    @Test
    void whenCreateNote_thenStatus201() throws Exception {

        LocalDateTime currentDate = DateUtils.getCurrentDate();
        note = Note.builder()
                .title(title)
                .content(content)
                .build();

        mvc.perform(post("/api/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(note)))
                .andExpect(status().isCreated());

        List<Note> notes = noteRepo.findAll();
        Note note = notes.get(0);

        assertThat(notes.size()).isEqualTo(1);
        assertThat(note.getVersion()).isEqualTo(versionOne);
        assertThat(note.getTitle()).isEqualTo(title);
        assertThat(note.getContent()).isEqualTo(content);
        assertThat(note.getCreated()).isEqualToIgnoringSeconds(currentDate);
    }


    @Test
    void whenCreateNote_thenStatus400() throws Exception {

        createNotes();
        note = Note.builder()
                .title(title)
                .content(content)
                .build();

        mvc.perform(post("/api/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(note)))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    private void createNotes() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(Note.builder()
                .title(title)
                .content(content)
                .version(1L)
                .created(DateUtils.getCurrentDate())
                .build());

        noteList.add(Note.builder()
                .title(title2)
                .content(content2)
                .version(1L)
                .created(DateUtils.getCurrentDate())
                .build());

        for (Note note : noteList) {
            noteRepo.saveAndFlush(note);
        }
        log.info("-- createTestEmployees");
    }
}