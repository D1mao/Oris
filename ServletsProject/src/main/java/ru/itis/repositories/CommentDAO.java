package ru.itis.repositories;

import ru.itis.models.Comment;

public interface CommentDAO extends CrudDAO<Comment>{
    Comment getCommentById(int id);
}
