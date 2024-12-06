package ru.itis.repositories;

import ru.itis.models.Project;
import ru.itis.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<User>{
    User getUserById(int id);
    User getUserByEmail(String email);
    boolean isEmailValid(String email);
    List<Project> getAllUserProjects(int user_id);
}
