package task2;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ORIS";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Нынешний список пользователей: ");
        List<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user.getId() + " " + user.getName());
        }

        System.out.println("Сколько пользователей хотите добавить?: ");
        Integer newUsersAmount = scanner.nextInt();

        Integer id;
        String name;
        String surname;
        Integer age;
        String car_make;
        String email;
        String home_address;

        Integer newUsersCounter = 0;

        if (users.isEmpty()) {
            id = 1;
        } else {
            id = users.get(users.size() - 1).getId() + 1;
        }

        for (int i = 0; i < newUsersAmount; i++) {
            System.out.print("Введите имя водителя: ");
            name = scanner.nextLine();
            System.out.print("Введите фамилию водителя: ");
            surname = scanner.nextLine();
            System.out.print("Введите возраст водителя: ");
            age = scanner.nextInt();
            System.out.print("Введите марку машины водителя: ");
            car_make = scanner.nextLine();
            System.out.print("Введите почту водителя: ");
            email = scanner.nextLine();
            System.out.print("Введите домашний адрес водителя: ");
            home_address = scanner.nextLine();
            User user = new User(id, name, surname, age, car_make, email, home_address);
            userRepository.save(user);
            id++;
            newUsersCounter++;
        }

        System.out.println("Добавлено " + newUsersCounter + " пользователей");

        Integer test_id = 1;
        Optional<User> test_user = userRepository.findById(test_id);
        System.out.print("Пользователь с id " + test_id + ": ");
        if (test_user.isPresent()) {
            System.out.println(test_user.get().getName() + " " + test_user.get().getSurname());
        }

        User userUpdate = new User(1, "Kamil", "Kaifullin", 19, "BMW", "kaif@mail.ru", "Kazan, Street, 1");
        System.out.println("Обновим данные пользователя с id = 1 на: " + userUpdate.getName() + " "
                + userUpdate.getSurname() + " "
                + userUpdate.getAge() + " "
                + userUpdate.getCar_make() + " "
                + userUpdate.getEmail() + " "
                + userUpdate.getHome_address());
        userRepository.update(userUpdate);

        System.out.println("Удалим пользователя с id = 2");
        userRepository.removeById(2);

        System.out.println("Нынешний список пользователей: ");
        users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user.getId() + " " + user.getName());
        }

        System.out.println("Пользователи, с возрастом = 19: ");
        List<User> usersWithAge = userRepository.findAllByAge(19);
        for (User user : usersWithAge) {
            System.out.println(user.getId() + " " + user.getName());
        }

        System.out.println("Пользователи, с маркой Volkswagen: ");
        List<User> usersWithCarMake = userRepository.findAllByCarMake("Volkswagen");
        for (User user : usersWithCarMake) {
            System.out.println(user.getId() + " " + user.getName());
        }

        System.out.println("Пользователь, с почтой D1mao@mail.ru: ");
        Optional<User> userWithEmail = userRepository.findByEmail("D1mao@mail.ru");
        if (userWithEmail.isPresent()) {
            System.out.println(userWithEmail.get().getId() + " " + userWithEmail.get().getName());
        }
    }
}
