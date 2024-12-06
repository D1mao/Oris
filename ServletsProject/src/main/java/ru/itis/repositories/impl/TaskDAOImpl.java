package ru.itis.repositories.impl;

import ru.itis.models.Comment;
import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.repositories.TaskDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    private Connection connection;

    private static final String SQL_SELECT_ALL = "SELECT * FROM tasks";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM tasks WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO tasks (title, description, status, due_date, project_id, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM tasks WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE tasks SET title = ?, description = ?, status = ?, due_date = ? WHERE id = ?";
    private static final String SQL_SELECT_ALL_TASK_COMMENTS = "SELECT * FROM comments WHERE task_id = ?";

    @Override
    public void save(Task task) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getStatus());
            preparedStatement.setDate(4, task.getDueDate());
            preparedStatement.setInt(5, task.getProjectId());
            preparedStatement.setInt(6, task.getUserId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Task task) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getStatus());
            preparedStatement.setDate(4, task.getDueDate());
            preparedStatement.setInt(5, task.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Task task) {
        int id = task.getId();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Task> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            List<Task> result = new ArrayList<>();

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
    public Task getTaskById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getDate("due_date"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getInt("project_id"),
                        resultSet.getInt("user_id"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Comment> getAllTaskComments(int taskId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_TASK_COMMENTS);
            preparedStatement.setInt(1, taskId);

            List<Comment> result = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("task_id"));
                result.add(comment);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
