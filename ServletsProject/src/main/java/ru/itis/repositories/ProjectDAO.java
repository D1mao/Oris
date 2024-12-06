package ru.itis.repositories;

import ru.itis.models.Project;
import ru.itis.models.Task;

import java.util.List;

public interface ProjectDAO extends CrudDAO <Project>{
    Project getProjectById(int id);
    List<Task> getAllProjectTasks(int projectId);
}
