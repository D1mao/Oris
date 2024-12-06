package ru.itis.repositories.impl;

import ru.itis.models.Comment;
import ru.itis.models.Project;
import ru.itis.repositories.CommentDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl implements CommentDAO {

    private Connection connection;

    private static final String SQL_SELECT_ALL = "SELECT * FROM comments";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM comments WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO comments (content, user_id, task_id) VALUES (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM comments WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE comments SET content = ? WHERE id = ?";

    @Override
    public Comment getCommentById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Comment(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("task_id"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Comment comment) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setInt(2, comment.getUserId());
            preparedStatement.setInt(3, comment.getTaskId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Comment comment) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setInt(2, comment.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Comment comment) {
        int id = comment.getId();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            List<Comment> result = new ArrayList<>();

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
