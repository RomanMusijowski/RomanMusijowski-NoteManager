package com.example.developerapi.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NoteDTO {

    private Long id;

    private String title;

    private String content;

    private Long version;

    private LocalDateTime created;

    private LocalDateTime modified;

    private LocalDateTime deleted;

    List<NoteSnapshotDTO> snapshots;
}
