package ru.itis.services;

import ru.itis.dto.ProjectDTO.ProjectDTO;
import ru.itis.dto.UserDTO.RegisterForm;
import ru.itis.dto.UserDTO.UserDTO;
import ru.itis.models.Project;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean registerUser(RegisterForm registerForm);
    UserDTO logInUser(String email, String password);
    Optional<UserDTO> getUserById(int id);
    List<UserDTO> getAllUsers() ;
    int getUserIdByEmail(String email);
    List<ProjectDTO> getAllUserProjects(int id);
}
