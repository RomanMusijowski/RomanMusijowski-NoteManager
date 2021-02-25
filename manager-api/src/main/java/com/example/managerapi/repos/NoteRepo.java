package com.example.managerapi.repos;

import com.example.managerapi.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    Page<Note> findAllByDeletedNull(Pageable pageable);

    boolean existsById(Long id);

    Optional<Note> findByIdAndDeletedNull(Long id);
}
