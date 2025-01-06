package ru.itis.services;

import ru.itis.dto.CommentDTO.CommentCreationDTO;

public interface CommentService {
    void addComment(CommentCreationDTO commentCreationDTO);
}
