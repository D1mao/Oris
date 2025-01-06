package ru.itis.services;

import ru.itis.dto.CommentDTO.CommentDTO;
import ru.itis.dto.TaskDTO.TaskCreationDTO;
import ru.itis.dto.TaskDTO.TaskDTO;
import ru.itis.models.Task;

import java.util.List;

public interface TaskService {
    void addTask(TaskCreationDTO taskCreationDTO);
    void changeTaskStatusForward(TaskDTO taskDTO);
    void changeTaskStatusBackward(TaskDTO taskDTO);
    TaskDTO getTaskById(int id);
    List<CommentDTO> getAllTaskComments(int taskId);
    TaskDTO taskToTaskDTO(Task task);
    void assignTask(TaskDTO taskDTO, int userId);
}
