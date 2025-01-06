package ru.itis.repositories;

import ru.itis.models.Project;
import ru.itis.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO extends CrudDAO<User>{
    Optional<User> getUserById(int id);
    User getUserByEmail(String email) throws SQLException;
    boolean isEmailValid(String email);
    List<Project> getAllUserProjects(int user_id);
    String getUserRoleName(User user);
}
