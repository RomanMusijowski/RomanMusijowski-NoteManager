package com.example.developerapi.controllers;

import com.example.developerapi.DeveloperApiApplication;
import com.example.developerapi.domain.Note;
import com.example.developerapi.domain.NoteSnapshot;
import com.example.developerapi.repos.NoteRepo;
import com.example.developerapi.util.DateUtils;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
@RunWith(SpringRunner.class)
@ActiveProfiles("integration-test")
@SpringBootTest(
        classes = DeveloperApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-integration-test.yaml")
@Transactional
class NoteHistoryControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private NoteRepo noteRepo;

    private final static String API_NOTE_HISTORY = "/api/note/history/";
    private final static Long VERSION_ONE = 1L;
    private final static Long VERSION_TWO = 2L;
    private final static String TITLE = "title";
    private final static String TITLE_2 = "title2";
    private final static String CONTENT = "content";
    private final static String CONTENT_2 = "content2";

    @AfterEach
    void tearDown() {
        noteRepo.deleteAll();
        log.info("-- tearDown");
    }

    @Test
    void whenGetNote_thenReturn200() throws Exception {
        Note updateNote = updateNote(createNote().getId());

        mvc.perform(get(API_NOTE_HISTORY + updateNote.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(TITLE_2))
                .andExpect(jsonPath("$.content").value(CONTENT_2))
                .andExpect(jsonPath("$.version").value(VERSION_TWO))
                .andExpect(jsonPath("$.deleted").isEmpty())
                .andExpect(jsonPath("$.snapshots").value(hasSize(1)))
                .andExpect(jsonPath("$.snapshots[0].version").value(VERSION_ONE))
                .andExpect(jsonPath("$.snapshots[0].title").value(TITLE))
                .andExpect(jsonPath("$.snapshots[0].content").value(CONTENT));
    }

    @Test
    void whenGetDeletedNote_thenReturn200() throws Exception {
        Note deleteNote = updateNote(createNote().getId());
        deleteNote(deleteNote.getId());

        mvc.perform(get(API_NOTE_HISTORY + deleteNote.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(TITLE_2))
                .andExpect(jsonPath("$.content").value(CONTENT_2))
                .andExpect(jsonPath("$.version").value(VERSION_TWO))
                .andExpect(jsonPath("$.deleted").isNotEmpty())
                .andExpect(jsonPath("$.snapshots").value(hasSize(1)))
                .andExpect(jsonPath("$.snapshots[0].version").value(VERSION_ONE))
                .andExpect(jsonPath("$.snapshots[0].title").value(TITLE))
                .andExpect(jsonPath("$.snapshots[0].content").value(CONTENT));
    }

    @Test
    void whenGetUnExistedNote_thenReturn400() throws Exception {
        mvc.perform(get("/api/note/history/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private Note createNote() {
        Note note1 = Note.builder()
                .title(TITLE)
                .content(CONTENT)
                .version(VERSION_ONE)
                .created(DateUtils.getCurrentDate())
                .snapshots(new ArrayList<>())
                .build();
        return noteRepo.saveAndFlush(note1);
    }

    private Note updateNote(Long id) {
        LocalDateTime currentDate = DateUtils.getCurrentDate();
        Note note = noteRepo.getOne(id);

        note.getSnapshots().add(NoteSnapshot.builder()
                .note(note)
                .title(note.getTitle())
                .content(note.getContent())
                .modified(currentDate)
                .version(note.getVersion())
                .build()
        );
        note.setTitle(TITLE_2);
        note.setContent(CONTENT_2);
        note.setVersion(note.getVersion() + 1);
        note.setModified(currentDate);
        return noteRepo.saveAndFlush(note);
    }

    private void deleteNote(Long id) {
        Note note = noteRepo.findById(id).get();
        note.setDeleted(DateUtils.getCurrentDate());
        noteRepo.saveAndFlush(note);
    }
}