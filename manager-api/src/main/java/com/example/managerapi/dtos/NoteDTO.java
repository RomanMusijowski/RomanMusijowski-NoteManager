package com.example.managerapi.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDTO {

    private Long id;

    private String title;

    private String content;

    private Long version;

    private LocalDateTime created;

    private LocalDateTime modified;
}
