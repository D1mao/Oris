package ru.itis.repositories;

import ru.itis.models.Project;
import ru.itis.models.Task;
import ru.itis.models.User;

import java.util.List;

public interface ProjectDAO extends CrudDAO <Project>{
    Project getProjectById(int id);
    List<Task> getAllProjectTasks(int id);
    List<User> getAllProjectUsers(int id);
    void addUserToProject(int project_id, int user_id);
}
