package com.example.managerapi.repos;

import com.example.managerapi.domain.Note;
import com.example.managerapi.dtos.NoteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    List<Note> findAllByDeletedNull();

    boolean existsById(Long id);

    Optional<Note> findByIdAndDeletedNull(Long id);
}
