package ru.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.models.Comment;
import ru.itis.models.Project;
import ru.itis.repositories.CommentDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommentDAOImpl implements CommentDAO {

    private final DataSource dataSource;

    private static final String SQL_SELECT_ALL = "SELECT * FROM comments";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM comments WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO comments (content, user_id, task_id, file_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM comments WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE comments SET content = ? WHERE id = ?";
    private static final String SQL_FIND_COMMENT_USER_NAME = "SELECT username FROM users WHERE id = ?";

    @Override
    public Comment getCommentById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Comment(
                        resultSet.getString("content"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("task_id"),
                        resultSet.getInt("file_id"),
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
    public String getCommentUserName(Comment comment) {
        int id = comment.getId();
        String userName = "";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("user_id");

                PreparedStatement userStatement = connection.prepareStatement(SQL_FIND_COMMENT_USER_NAME);
                userStatement.setInt(1, userId);

                ResultSet userResultSet = userStatement.executeQuery();
                if (userResultSet.next()) {
                    userName = userResultSet.getString("username");
                }
            }

            return userName;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Comment comment) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setInt(2, comment.getUserId());
            preparedStatement.setInt(3, comment.getTaskId());
            preparedStatement.setInt(4, comment.getFileId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Comment comment) {
        try (Connection connection = dataSource.getConnection()) {
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

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            List<Comment> result = new ArrayList<>();

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
