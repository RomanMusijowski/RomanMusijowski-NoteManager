package com.example.developerapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "NOTES")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "VERSION", nullable = false)
    private Long version;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
    List<NoteSnapshot> snapshots;

    @Column(name = "DELETED_DATE_TIME")
    private LocalDateTime deleted;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime created;

    @Column(name = "MODIFIED_DATE_TIME")
    private LocalDateTime modified;
}
