package ru.itis.services;

import ru.itis.dto.ProjectDTO.ProjectCreationDTO;
import ru.itis.dto.ProjectDTO.ProjectDTO;
import ru.itis.dto.TaskDTO.TaskDTO;
import ru.itis.dto.UserDTO.UserDTO;
import ru.itis.models.Project;

import java.util.List;

public interface ProjectService {
    void addProject(ProjectCreationDTO projectCreationDTO);
    List<Project> getAllProjects();
    ProjectDTO getProjectById(int id);
    List<UserDTO> getAllProjectUsers(int id);
    public List<TaskDTO> getAllProjectTasks(int id);
}
