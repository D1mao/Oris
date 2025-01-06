package ru.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.models.Comment;
import ru.itis.models.Task;
import ru.itis.repositories.TaskDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TaskDAOImpl implements TaskDAO {

    private final DataSource dataSource;

    private static final String SQL_SELECT_ALL = "SELECT * FROM tasks";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM tasks WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO tasks (title, description, status_id, due_date, project_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM tasks WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE tasks SET title = ?, description = ?, status_id = ?, due_date = ? WHERE id = ?";
    private static final String SQL_SELECT_ALL_TASK_COMMENTS = "SELECT * FROM comments WHERE task_id = ?";
    private static final String SQL_FIND_TASK_STATUS_NAME = "SELECT ts.name " +
                                                            "FROM Tasks t " +
                                                            "JOIN Task_States ts ON t.status_id = ts.id " +
                                                            "WHERE t.id = ?";
    private static final String SQL_FIND_TASK_ASSIGNED_USER_NAME = "SELECT username FROM users WHERE id = ?";

    @Override
    public void save(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setInt(3, 1);
            preparedStatement.setDate(4, task.getDueDate());
            preparedStatement.setInt(5, task.getProjectId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setInt(3, task.getStatusId());
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

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getTaskStatusName(Task task) {
        int id = task.getId();
        String statusName = "";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_TASK_STATUS_NAME);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                statusName = resultSet.getString("name");
            }

            return statusName;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getTaskAssignedUserName(Task task) {
        int id = task.getId();
        String assignedUserName = null;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("user_id");
                if (resultSet.wasNull() || userId == null) {
                    return null;
                }

                PreparedStatement userStatement = connection.prepareStatement(SQL_FIND_TASK_ASSIGNED_USER_NAME);
                userStatement.setInt(1, userId);

                ResultSet userResultSet = userStatement.executeQuery();
                if (userResultSet.next()) {
                    assignedUserName = userResultSet.getString("username");
                }
            }

            return assignedUserName;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Task> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            List<Task> result = new ArrayList<>();

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
    public Task getTaskById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Task(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("due_date"),
                        resultSet.getInt("project_id"),
                        resultSet.getInt("file_id"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getInt("status_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("id"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Comment> getAllTaskComments(int taskId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_TASK_COMMENTS);
            preparedStatement.setInt(1, taskId);

            List<Comment> result = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getString("content"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("task_id"),
                        resultSet.getInt("file_id"),
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("created_at"));
                result.add(comment);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
