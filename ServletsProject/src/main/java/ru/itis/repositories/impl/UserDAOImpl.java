package ru.itis.repositories.impl;

import ru.itis.models.Project;
import ru.itis.models.User;
import ru.itis.repositories.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private Connection connection;

    private static final String SQL_SELECT_ALL = "SELECT * FROM users";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SQL_INSERT = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM users WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String SQL_CHECK_EMAIL_VALIDITY = "SELECT COUNT(*) FROM users WHERE email = ?";
    private static final String SQL_SELECT_ALL_USER_PROJECTS = "SELECT p.id, p.name, p.description, p.created_at, p.created_by " +
                                                                "FROM Projects p " +
                                                                "JOIN User_Projects up ON p.id = up.project_id " +
                                                                "WHERE up.user_id = ?";

    @Override
    public User getUserById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_EMAIL);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean isEmailValid(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_EMAIL_VALIDITY);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return false;
    }

    @Override
    public List<Project> getAllUserProjects(int user_id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USER_PROJECTS);
            preparedStatement.setInt(1, user_id);

            List<Project> result = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getInt("created_by"));
                result.add(project);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(User user) {
        int id = user.getId();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
