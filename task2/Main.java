import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ORIS";

    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from drivers");

            while (result.next()) {
                System.out.println(result.getInt("id") + " " + result.getString("name"));
            }

            Scanner scanner = new Scanner(System.in);

            String firstName;
            String secondName;
            int age;

            String sqlInsertUser = "insert into drivers(name, surname, age)" +
                    "values (?,?,?)";

            for (int i = 0; i < 6; i++) {
                scanner.nextLine();
                System.out.print("Введите имя водителя: ");
                firstName = scanner.nextLine();
                System.out.print("Введите фамилию водителя: ");
                secondName = scanner.nextLine();
                System.out.print("Введите возраст водителя: ");
                age = scanner.nextInt();

                PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, secondName);
                preparedStatement.setInt(3, age);

                preparedStatement.executeUpdate();
            }

            ResultSet rs = statement.executeQuery("select * from drivers WHERE age > 25");
            System.out.println("Водители старше 25:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("age"));
            }
        } catch (SQLException | InputMismatchException e){
            e.printStackTrace();
        }
    }
}
