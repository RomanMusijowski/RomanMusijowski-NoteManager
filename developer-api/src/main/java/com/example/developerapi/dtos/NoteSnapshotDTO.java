package com.example.developerapi.dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteSnapshotDTO {

    private Long id;

    private String content;

    private Long version;

    private LocalDateTime modified;
}
