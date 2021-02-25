package com.example.managerapi.services;

import com.example.managerapi.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    Page<Note> findAll(Pageable pageable);

    Note findById(Long id);

    void create(String title, String content);

    Note update(Long id, String title, String content);

    void deleteById(Long id);
}
