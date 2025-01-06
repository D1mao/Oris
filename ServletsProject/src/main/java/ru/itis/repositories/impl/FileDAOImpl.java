package ru.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FileDAOImpl implements FileDAO {
    private final DataSource dataSource;

    private static final String SQL_SELECT_ALL = "SELECT * FROM files";
    private static final String SQL_INSERT = "insert into files(storage_file_name, original_file_name, type, size) " +
            "values (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "select * from files where file_id = ?";
    private static final String SQL_DELETE = "DELETE FROM files WHERE file_id = ?";

    @Override
    public FileInfo getById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new FileInfo(
                        resultSet.getString("original_file_name"),
                        resultSet.getInt("size"),
                        resultSet.getString("type"),
                        resultSet.getInt("file_id"),
                        resultSet.getString("storage_file_name"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(FileInfo file) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, file.getStorageFileName());
            preparedStatement.setString(2, file.getOriginalFileName());
            preparedStatement.setString(3, file.getType());
            preparedStatement.setInt(4, file.getSize());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(FileInfo file) {

    }

    @Override
    public void delete(FileInfo file) {
        int id = file.getId();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<FileInfo> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            List<FileInfo> result = new ArrayList<>();

            while (resultSet.next()) {
                FileInfo file = new FileInfo(
                        resultSet.getString("original_file_name"),
                        resultSet.getInt("size"),
                        resultSet.getString("type"),
                        resultSet.getInt("file_id"),
                        resultSet.getString("storage_file_name"));
                result.add(file);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
