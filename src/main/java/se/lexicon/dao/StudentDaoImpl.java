package se.lexicon.dao;

import se.lexicon.db.DatabaseConnection;
import se.lexicon.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    private Connection connection;

    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student save(Student student) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO student (name, class_group) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
        ) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getClassGroup());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating student failed, no rows affected.");
                // add custom exception as needed
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            IO.println("Error saving student: " + e.getSQLState());
            e.printStackTrace();
            // add custom exception as needed
        }

        return student; // Output: // name,classGroup
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = null;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            students = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String classGroup = resultSet.getString("class_group");
                LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                Student student = new Student(id, name, classGroup, createDate);
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // throw a custom exception if needed
        }
        return students;

    }
}
