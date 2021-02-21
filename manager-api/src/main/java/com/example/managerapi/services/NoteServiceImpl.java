package com.example.managerapi.services;

import com.example.managerapi.domain.Note;
import com.example.managerapi.domain.NoteSnapshot;
import com.example.managerapi.dtos.NoteDTO;
import com.example.managerapi.repos.NoteRepo;
import com.example.managerapi.util.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;
    private static final Long NEW_NOTE_VERSION = 1L;

    @Override
    public List<Note> findAll() {
        return noteRepo.findAllByDeletedNull();
    }

    @Override
    public Note findById(Long id) {
        return noteRepo.findByIdAndDeletedNull(id)
                .orElseThrow(()-> new EntityNotFoundException("Note with id - " + id + " doesn't exist."));
    }

    @Override
    public void create(String title, String content) {
        Note note = Note.builder()
                .title(title)
                .content(content)
                .created(DateUtils.getCurrentDate())
                .version(NEW_NOTE_VERSION)
                .snapshots(Collections.emptyList())
                .build();
        noteRepo.save(note);
    }

    @Override
    public Note update(Long id, String title, String content) {
        Note note = findById(id);
        note.getSnapshots().add(NoteSnapshot.builder()
                .note(note)
                .title(title)
                .content(note.getContent())
                .modified(DateUtils.getCurrentDate())
                .version(note.getVersion())
                .build()
        );

        note.setTitle(title);
        note.setContent(content);
        note.setVersion(note.getVersion() + NEW_NOTE_VERSION);
        note.setModified(DateUtils.getCurrentDate());

        return noteRepo.save(note);
    }

    @Override
    public void deleteById(Long id) {
        Note note = findById(id);
        note.setDeleted(DateUtils.getCurrentDate());
        noteRepo.save(note);
    }
}
