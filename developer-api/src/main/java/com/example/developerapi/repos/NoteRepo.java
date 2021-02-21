package com.example.developerapi.repos;


import com.example.developerapi.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

}
