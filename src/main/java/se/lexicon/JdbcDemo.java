package se.lexicon;

import java.sql.*;
import java.time.LocalDateTime;

public class JdbcDemo {

    void main() {
        ex1();
    }


    static void ex1() {
        try {
            // Step 1
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_db",
                    "root",
                    "root"
            );

            // Step 2
            Statement statement = connection.createStatement();
            String query = "SELECT id, name, class_group, create_date FROM student";
            ResultSet resultSet = statement.executeQuery(query);
            // executeQuery method used for SELECT statements & returns ResultSet
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String classGroup = resultSet.getString("class_group");
                LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                IO.println("Id: " + id + ", Name: " + name + ", Class Group: " + classGroup + ", Create Date: " + createDate);
            }

            IO.println("Database Connection established successfully!");
        } catch (SQLException e) {
            IO.println(e.getMessage());
            e.printStackTrace(); // debugging the code
        }

    }
}
