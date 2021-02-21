package com.example.developerapi.controllers;

import com.example.developerapi.domain.Note;
import com.example.developerapi.dtos.NoteDTO;
import com.example.developerapi.services.NoteHistoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@AllArgsConstructor
@RequestMapping("api/note/history")
@Validated
public class NoteHistoryController {

    private final NoteHistoryService noteService;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public NoteDTO getNote(@PathVariable("id") @Min(value = 1, message = "id must be greater than or equal to 1") Long id) {
        Note note = noteService.findById(id);
        return mapper.map(note, NoteDTO.class);
    }
}
