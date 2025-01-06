package ru.itis.services.impl;

import ru.itis.dto.CommentDTO.CommentCreationDTO;
import ru.itis.models.Comment;
import ru.itis.repositories.CommentDAO;
import ru.itis.services.CommentService;

public class CommentServiceImpl implements CommentService {
    private CommentDAO commentDAO;

    public CommentServiceImpl(CommentDAO commentDAO) {this.commentDAO = commentDAO;}

    public void addComment(CommentCreationDTO commentCreationDTO) {
        Comment comment = Comment.builder()
                .content(commentCreationDTO.getContent())
                .userId(commentCreationDTO.getUserId())
                .taskId(commentCreationDTO.getTaskId())
                .build();

        commentDAO.save(comment);
    }
}
