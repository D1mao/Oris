package ru.itis.services.impl;

import org.mindrot.jbcrypt.BCrypt;
import ru.itis.dto.ProjectDTO.ProjectDTO;
import ru.itis.dto.UserDTO.RegisterForm;
import ru.itis.dto.UserDTO.UserDTO;
import ru.itis.models.Project;
import ru.itis.models.User;
import ru.itis.repositories.ProjectDAO;
import ru.itis.repositories.UserDAO;
import ru.itis.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private ProjectDAO projectDAO;
    public UserServiceImpl(UserDAO userDAO, ProjectDAO projectDAO){this.userDAO = userDAO;
    this.projectDAO = projectDAO;}

    public boolean registerUser(RegisterForm registerForm){
        String email = registerForm.getEmail();

        if (userDAO.isEmailValid(email)){
            return false;
        }

        String hashedPassword = BCrypt.hashpw(registerForm.getPassword(), BCrypt.gensalt());

        User user = User.builder()
                .username(registerForm.getUsername())
                .email(email)
                .password(hashedPassword)
                .roleId(Integer.parseInt(registerForm.getRoleId()))
                .build();

        userDAO.save(user);

        return true;
    }

    public UserDTO logInUser(String email, String password){
        try {
            User user = userDAO.getUserByEmail(email);
            if(user != null && BCrypt.checkpw(password, user.getPassword())){
                return userToDTO(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserDTO userToDTO(User user){
        if(user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(userDAO.getUserRoleName(user))
                .build();
    }

    public Optional<UserDTO> getUserById(int id) {
        Optional<User> userOptional = userDAO.getUserById(id);
        return userOptional.map(this::userToDTO);
    }

    public List<UserDTO> getAllUsers()  {
        List<User> users = userDAO.getAll();
        return users.stream()
                .map(this::userToDTO)
                .collect(Collectors.toList());
    }

    public int getUserIdByEmail(String email) {
        try {
            return userDAO.getUserByEmail(email).getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ProjectDTO> getAllUserProjects(int id) {
        List<Project> projects = userDAO.getAllUserProjects(id);

        return projects.stream()
                .map(this::projectToDTO)
                .collect(Collectors.toList());
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
