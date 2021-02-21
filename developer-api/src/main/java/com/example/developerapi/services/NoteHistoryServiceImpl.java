package com.example.developerapi.services;

import com.example.developerapi.domain.Note;
import com.example.developerapi.repos.NoteRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class NoteHistoryServiceImpl implements NoteHistoryService {

    private final NoteRepo noteRepo;

    @Override
    public Note findById(Long id) {
        return noteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note with id - " + id + " doesn't exist."));
    }
}
