package com.example.managerapi.controllers;


import com.example.managerapi.domain.Note;
import com.example.managerapi.dtos.NoteDTO;
import com.example.managerapi.payload.NotePayload;
import com.example.managerapi.services.NoteService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/note")
@Validated
public class NoteController {

    private final NoteService noteService;
    private final ModelMapper mapper;

    @GetMapping
    public List<NoteDTO> getAllNotes() {
        return noteService.findAll()
                .stream()
                .map(note -> mapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable(name = "id") @Min(value = 1, message = "id must be greater than or equal to 1") Long id) {
        return mapper.map(noteService.findById(id), NoteDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteDTO updateNote(@PathVariable("id") @Min(value = 1, message = "id must be greater than or equal to 1") Long id,
                              @RequestBody @Valid NotePayload payload) {
        Note update = noteService.update(id, payload.getTitle(), payload.getContent());
        return mapper.map(update, NoteDTO.class);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestBody @Valid NotePayload payload) {
        noteService.create(payload.getTitle(), payload.getContent());
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") @Min(value = 1, message = "id must be greater than or equal to 1") Long id) {
        noteService.deleteById(id);
    }

}
