package com.example.managerapi.services;

import com.example.managerapi.domain.Note;

import java.util.List;

public interface NoteService {

    List<Note> findAll();

    Note findById(Long id);

    void create(String content);

    Note update(Long id, String content);

    void deleteById(Long id);
}
