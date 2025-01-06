package ru.itis.services.impl;

import ru.itis.dto.ProjectDTO.ProjectCreationDTO;
import ru.itis.dto.ProjectDTO.ProjectDTO;
import ru.itis.dto.TaskDTO.TaskDTO;
import ru.itis.dto.UserDTO.UserDTO;
import ru.itis.models.Project;
import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.repositories.ProjectDAO;
import ru.itis.repositories.UserDAO;
import ru.itis.repositories.impl.UserDAOImpl;
import ru.itis.repositories.TaskDAO;
import ru.itis.repositories.impl.TaskDAOImpl;
import ru.itis.services.ProjectService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {
    private ProjectDAO projectDAO;
    private UserDAO userDAO;
    private TaskDAO taskDAO;

    public ProjectServiceImpl(ProjectDAO projectDAO, UserDAO userDAO, TaskDAO taskDAO){this.projectDAO = projectDAO;
    this.userDAO = userDAO;
    this.taskDAO = taskDAO;}

    @Override
    public void addProject(ProjectCreationDTO projectCreationDTO) {
        Project project = Project.builder()
                .name(projectCreationDTO.getName())
                .description(projectCreationDTO.getDescription())
                .createdBy(projectCreationDTO.getCreatedBy())
                .build();

        projectDAO.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDAO.getAll();
    }

    @Override
    public ProjectDTO getProjectById(int id) {
        return projectToDTO(projectDAO.getProjectById(id));
    }

    @Override
    public List<UserDTO> getAllProjectUsers(int id) {
        List<User> projectUsers = projectDAO.getAllProjectUsers(id);

        return projectUsers.stream()
                .map(this::userToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO userToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(userDAO.getUserRoleName(user))
                .build();
    }

    public List<TaskDTO> getAllProjectTasks(int id) {
        List<Task> projectTasks = projectDAO.getAllProjectTasks(id);

        return projectTasks.stream()
                .map(this::taskToDTO)
                .collect(Collectors.toList());
    }

    private TaskDTO taskToDTO(Task task) {
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

    private ProjectDTO projectToDTO(Project project) {
        Optional<User> createdByUser = userDAO.getUserById(project.getCreatedBy());

        if (createdByUser.isPresent()) {
            String username = createdByUser.get().getUsername();

            return ProjectDTO.builder()
                    .name(project.getName())
                    .description(project.getDescription())
                    .createdBy(username)
                    .id(project.getId())
                    .createdAt(project.getCreatedAt())
                    .build();
        }
        return null;
    }
}
