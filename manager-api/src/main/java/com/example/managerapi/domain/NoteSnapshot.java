package com.example.managerapi.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "NOTE_SNAPSHOTS")
@NoArgsConstructor
@AllArgsConstructor
public class NoteSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "VERSION", nullable = false)
    private Long version;

    @ManyToOne
    @JoinColumn(name = "NOTE_ID")
    @ToString.Exclude
    private Note note;

    @Column(name = "MODIFIED_DATE_TIME", nullable = false)
    private LocalDateTime modified;
}
