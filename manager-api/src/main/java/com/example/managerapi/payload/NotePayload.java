package com.example.managerapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class NotePayload {

    @NotEmpty
    @Size(min = 4, max = 64, message = "title size has to be 4-64 characters")
    private String title;

    @NotEmpty
    @Size(min = 4, max = 256, message = "content size has to be 4-256 characters")
    private String content;
}
