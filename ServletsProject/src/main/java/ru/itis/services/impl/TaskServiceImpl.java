package ru.itis.services.impl;

import ru.itis.dto.CommentDTO.CommentDTO;
import ru.itis.dto.TaskDTO.TaskCreationDTO;
import ru.itis.dto.TaskDTO.TaskDTO;
import ru.itis.models.Comment;
import ru.itis.models.Task;
import ru.itis.repositories.CommentDAO;
import ru.itis.repositories.TaskDAO;
import ru.itis.services.TaskService;

import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {
    private TaskDAO taskDAO;
    private CommentDAO commentDAO;

    public TaskServiceImpl(TaskDAO taskDAO, CommentDAO commentDAO) {this.taskDAO = taskDAO;
    this.commentDAO = commentDAO;}

    @Override
    public void addTask(TaskCreationDTO taskCreationDTO) {
        Task task = Task.builder()
                .title(taskCreationDTO.getTitle())
                .description(taskCreationDTO.getDescription())
                .dueDate(taskCreationDTO.getDueDate())
                .projectId(taskCreationDTO.getProjectId())
                .fileId(taskCreationDTO.getFileId())
                .build();
    }

    @Override
    public void changeTaskStatusForward(TaskDTO taskDTO) {
        Task task = taskDAO.getTaskById(taskDTO.getId());
        int newTaskStatusId = task.getStatusId() + 1;
        task.setStatusId(newTaskStatusId);
        taskDAO.update(task);
    }

    @Override
    public void changeTaskStatusBackward(TaskDTO taskDTO) {
        Task task = taskDAO.getTaskById(taskDTO.getId());
        int newTaskStatusId = task.getStatusId() - 1;
        task.setStatusId(newTaskStatusId);
        taskDAO.update(task);
    }

    @Override
    public TaskDTO getTaskById(int id) {
        Task task = taskDAO.getTaskById(id);
        return taskToTaskDTO(task);
    }

    @Override
    public List<CommentDTO> getAllTaskComments(int taskId) {
        List<Comment> comments = taskDAO.getAllTaskComments(taskId);

        return comments.stream()
                .map(this::commentToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO taskToTaskDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(taskDAO.getTaskStatusName(task))
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .projectId(task.getProjectId())
                .assignedUser(taskDAO.getTaskAssignedUserName(task))
                .fileId(task.getFileId())
                .build();
    }

    @Override
    public void assignTask(TaskDTO taskDTO, int userId) {
        Task task = taskDAO.getTaskById(taskDTO.getId());
        task.setUserId(userId);
        taskDAO.update(task);
    }

    private CommentDTO commentToDTO(Comment comment) {
        return CommentDTO.builder()
                .content(comment.getContent())
                .userName(commentDAO.getCommentUserName(comment))
                .taskId(comment.getTaskId())
                .id(comment.getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
