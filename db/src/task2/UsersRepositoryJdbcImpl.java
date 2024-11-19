package task2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_ALL_FROM_DRIVER = "SELECT * FROM drivers";
    private static final String SQL_REMOVE_USER_BY_ID = "DELETE FROM drivers WHERE id = ?";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE drivers " +
            "SET name = ?, surname = ?, age = ?, car_make = ?, email = ?, home_address = ? WHERE id = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO drivers(id, name, surname, age, car_make, email, home_address)" +
            "VALUES (?,?,?,?,?,?,?)";

    public UsersRepositoryJdbcImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<User> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

            List<User> result = new ArrayList<>();

            while (resulSet.next()) {
                User user = new User(
                        resulSet.getInt("id"),
                        resulSet.getString("name"),
                        resulSet.getString("surname"),
                        resulSet.getInt("age"),
                        resulSet.getString("car_make"),
                        resulSet.getString("email"),
                        resulSet.getString("home_adress"));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER + " WHERE id = " + id);
            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getString("car_make"),
                        resultSet.getString("email"),
                        resultSet.getString("home_adress"));
                result.add(user);
            }

            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getSurname());
            preparedStatement.setInt(4, entity.getAge());
            preparedStatement.setString(5, entity.getCar_make());
            preparedStatement.setString(6, entity.getEmail());
            preparedStatement.setString(7, entity.getHome_address());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setString(4, entity.getCar_make());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getHome_address());
            preparedStatement.setInt(7, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void remove(User entity) {
        Integer id = entity.getId();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_USER_BY_ID);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void removeById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_USER_BY_ID);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER + " WHERE age = " + age);

            List<User> result = new ArrayList<>();

            while (resulSet.next()) {
                User user = new User(
                        resulSet.getInt("id"),
                        resulSet.getString("name"),
                        resulSet.getString("surname"),
                        resulSet.getInt("age"),
                        resulSet.getString("car_make"),
                        resulSet.getString("email"),
                        resulSet.getString("home_adress"));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAllByCarMake(String car_make) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER + " WHERE car_make = " + car_make);

            List<User> result = new ArrayList<>();

            while (resulSet.next()) {
                User user = new User(
                        resulSet.getInt("id"),
                        resulSet.getString("name"),
                        resulSet.getString("surname"),
                        resulSet.getInt("age"),
                        resulSet.getString("car_make"),
                        resulSet.getString("email"),
                        resulSet.getString("home_adress"));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAllByHomeAddress(String home_address) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER + " WHERE home_address = " + home_address);

            List<User> result = new ArrayList<>();

            while (resulSet.next()) {
                User user = new User(
                        resulSet.getInt("id"),
                        resulSet.getString("name"),
                        resulSet.getString("surname"),
                        resulSet.getInt("age"),
                        resulSet.getString("car_make"),
                        resulSet.getString("email"),
                        resulSet.getString("home_adress"));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER + " WHERE email = " + email);
            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getString("car_make"),
                        resultSet.getString("email"),
                        resultSet.getString("home_adress"));
                result.add(user);
            }

            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
