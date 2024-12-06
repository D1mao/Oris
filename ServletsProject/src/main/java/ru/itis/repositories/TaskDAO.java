package ru.itis.repositories;

import ru.itis.models.Comment;
import ru.itis.models.Task;

import java.util.List;

public interface TaskDAO extends CrudDAO<Task>{
    Task getTaskById(int id);
    List<Comment> getAllTaskComments(int taskId);
}
