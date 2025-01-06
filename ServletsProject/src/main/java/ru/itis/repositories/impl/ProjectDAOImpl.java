package ru.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.models.Comment;
import ru.itis.models.Project;
import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.repositories.ProjectDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProjectDAOImpl implements ProjectDAO {

    private final DataSource dataSource;

    private static final String SQL_SELECT_ALL = "SELECT * FROM projects";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM projects WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO projects (name, description, created_by) VALUES (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM projects WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE projects SET name = ?, description = ? WHERE id = ?";
    private static final String SQL_SELECT_ALL_PROJECT_TASKS = "SELECT * FROM tasks WHERE project_id = ?";
    private static final String SQL_SELECT_ALL_PROJECT_USERS = "SELECT u.id, u.username, u.email, u.password, u.role_id " +
                                                                "FROM Users u " +
                                                                "JOIN User_Projects up ON u.id = up.user_id " +
                                                                "WHERE up.project_id = ?";
    private static final String SQL_ADD_USER_TO_PROJECT = "INSERT INTO user_projects (user_id, project_id) VALUES (?, ?)";

    @Override
    public Project getProjectById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Project(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("created_by"),
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("created_at"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Task> getAllProjectTasks(int projectId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_PROJECT_TASKS);
            preparedStatement.setInt(1, projectId);

            List<Task> result = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("due_date"),
                        resultSet.getInt("project_id"),
                        resultSet.getInt("file_id"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getInt("status_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("id"));
                result.add(task);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> getAllProjectUsers(int projectId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_PROJECT_USERS);
            preparedStatement.setInt(1, projectId);

            List<User> result = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("role_id"),
                        resultSet.getInt("id"));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void addUserToProject(int project_id, int user_id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER_TO_PROJECT);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, project_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Project project) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setInt(3, project.getCreatedBy());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Project project) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setInt(3, project.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Project project) {
        int id = project.getId();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Project> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            List<Project> result = new ArrayList<>();

            while (resultSet.next()) {
                Project project = new Project(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("created_by"),
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("created_at"));
                result.add(project);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
