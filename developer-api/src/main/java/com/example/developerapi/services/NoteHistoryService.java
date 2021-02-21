package com.example.developerapi.services;

import com.example.developerapi.domain.Note;

public interface NoteHistoryService {

    Note findById(Long id);
}
