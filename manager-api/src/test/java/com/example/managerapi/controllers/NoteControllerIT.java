package com.example.managerapi.controllers;

import com.example.managerapi.ManagerApiApplication;
import com.example.managerapi.domain.Note;
import com.example.managerapi.domain.NoteSnapshot;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Note note;
    private final static String API_NOTE = "/api/note/";
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
    void whenGetNoteById_thenStatus200() throws Exception {
        List<Note> notes = createNotes();

        mvc.perform(get(API_NOTE + notes.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.content").value(CONTENT));
    }

    @Test
    void whenGetNoteById_thenStatus400() throws Exception {

        mvc.perform(get(API_NOTE + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    void whenGetDeleteNote_thenStatus400() throws Exception {
        Note note = createNote();
        deleteNote(note.getId());

        mvc.perform(get(API_NOTE + note.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenGetAllNotes_thenStatus200() throws Exception {
        createNotes();

        mvc.perform(get(API_NOTE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.*").value(hasSize(2)));
    }

    @Test
    void whenCreateNote_thenStatus201() throws Exception {

        LocalDateTime currentDate = DateUtils.getCurrentDate();
        note = Note.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();

        mvc.perform(post(API_NOTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(note)))
                .andExpect(status().isCreated());

        List<Note> notes = noteRepo.findAll();
        Note note = notes.get(0);

        assertThat(notes.size()).isOne();
        assertThat(note.getVersion()).isEqualTo(VERSION_ONE);
        assertThat(note.getTitle()).isEqualTo(TITLE);
        assertThat(note.getContent()).isEqualTo(CONTENT);
        assertThat(note.getCreated()).isEqualToIgnoringSeconds(currentDate);
    }


    @Test
    void whenCreateNoteWithDuplicateTitle_thenStatus400() throws Exception {

        createNotes();
        note = Note.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();

        mvc.perform(post(API_NOTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(note)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    void whenUpdateNote_thenStatus204() throws Exception {
        Note savedNote = createNote();

        LocalDateTime currentDate = DateUtils.getCurrentDate();
        note = Note.builder()
                .title(TITLE_2)
                .content(CONTENT_2)
                .build();

        mvc.perform(put(API_NOTE + savedNote.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(note)))
                .andExpect(status().isNoContent())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(TITLE_2))
                .andExpect(jsonPath("$.version").value(VERSION_TWO))
                .andExpect(jsonPath("$.content").value(CONTENT_2));

        Note checkNote = noteRepo.findById(savedNote.getId()).get();
        List<NoteSnapshot> snapshots = checkNote.getSnapshots();

        assertThat(snapshots.size()).isOne();
        assertThat(checkNote.getModified()).isEqualToIgnoringSeconds(currentDate);
        assertThat(snapshots.get(0).getTitle()).isEqualTo(TITLE);
        assertThat(snapshots.get(0).getContent()).isEqualTo(CONTENT);
        assertThat(snapshots.get(0).getVersion()).isEqualTo(VERSION_ONE);
    }

    @Test
    void whenUpdateNoteWithDuplicateTitle_thenStatus400() throws Exception {
        Note savedNote = createNotes().get(1);
        note = Note.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();

        mvc.perform(put(API_NOTE + savedNote.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(note)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenDeleteNote_thenStatus204() throws Exception {
        Note note = createNote();
        mvc.perform(delete(API_NOTE + note.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        List<Note> notes = noteRepo.findAll();
        assertThat(notes.size()).isOne();
        assertThat(notes.get(0).getDeleted()).isNotNull();
    }

    private void deleteNote(Long id) {
        Note deleteNote = noteRepo.getOne(id);
        deleteNote.setDeleted(DateUtils.getCurrentDate());
        noteRepo.saveAndFlush(deleteNote);
    }

    private Note createNote() {
        LocalDateTime currentDate = DateUtils.getCurrentDate();
        Note note1 = Note.builder()
                .title(TITLE)
                .content(CONTENT)
                .version(1L)
                .created(currentDate)
                .modified(currentDate)
                .snapshots(new ArrayList<>())
                .build();
        return noteRepo.saveAndFlush(note1);
    }

    private List<Note> createNotes() {
        List<Note> noteList = new ArrayList<>();
        Note note1 = Note.builder()
                .title(TITLE)
                .content(CONTENT)
                .version(VERSION_ONE)
                .created(DateUtils.getCurrentDate())
                .build();
        noteList.add(noteRepo.saveAndFlush(note1));

        Note note2 = Note.builder()
                .title(TITLE_2)
                .content(CONTENT_2)
                .version(VERSION_ONE)
                .created(DateUtils.getCurrentDate())
                .build();
        noteList.add(noteRepo.saveAndFlush(note2));
        log.info("-- createTestEmployees");

        return noteList;
    }
}