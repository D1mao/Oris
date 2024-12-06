package ru.itis.repositories.impl;

import ru.itis.models.Comment;
import ru.itis.models.Project;
import ru.itis.models.Task;
import ru.itis.repositories.ProjectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl implements ProjectDAO {

    private Connection connection;

    private static final String SQL_SELECT_ALL = "SELECT * FROM projects";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM projects WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO projects (name, description, created_by) VALUES (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM projects WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE projects SET name = ?, description = ? WHERE id = ?";
    private static final String SQL_SELECT_ALL_PROJECT_TASKS = "SELECT * FROM tasks WHERE project_id = ?";

    @Override
    public Project getProjectById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getInt("created_by"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Task> getAllProjectTasks(int projectId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_PROJECT_TASKS);
            preparedStatement.setInt(1, projectId);

            List<Task> result = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getDate("due_date"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getInt("project_id"),
                        resultSet.getInt("user_id"));
                result.add(task);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Project project) {
        try {
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
        try {
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

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Project> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            List<Project> result = new ArrayList<>();

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
}
