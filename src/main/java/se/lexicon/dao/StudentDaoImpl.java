package se.lexicon.dao;


import se.lexicon.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of StudentDao.
 * <p>
 * This class demonstrates:
 * - Using DataSource instead of DriverManager
 * - Proper resource management using try-with-resources
 * - Clear separation of concerns
 */
public class StudentDaoImpl implements StudentDao {

    private final Connection connection;

    /**
     * Connection is injected from outside.
     * The DAO does NOT know how connections are created.
     */
    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Saves a new student to the database.
     * The database generates the ID automatically.
     */
    @Override
    public Student save(Student student) {

        String sql = "INSERT INTO student (name, class_group) VALUES (?, ?)";

        try (
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getClassGroup());

            ps.executeUpdate();

            // Read auto-generated ID
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    student.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error saving student: " + e.getMessage());
            throw new RuntimeException("Error saving student", e);
        }

        return student;
    }

    /**
     * Retrieves all students from the database.
     */
    @Override
    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("class_group"),
                        rs.getTimestamp("create_date").toLocalDateTime()
                ));

            }

        } catch (SQLException e) {
            System.err.println("❌ Error retrieving students: " + e.getMessage());
            throw new RuntimeException("Error retrieving students", e);
        }

        return students;
    }

    /**
     * Retrieves a student by ID.
     */
    @Override
    public Optional<Student> findById(int id) {

        String sql = "SELECT * FROM student WHERE id = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToStudent(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error retrieving student: " + e.getMessage());
            throw new RuntimeException("Error retrieving student", e);
        }

        return Optional.empty();
    }

    /**
     * Updates an existing student.
     */
    @Override
    public void update(Student student) {

        String sql = "UPDATE student SET name = ?, class_group = ? WHERE id = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getClassGroup());
            ps.setInt(3, student.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("❌ Error updating student: " + e.getMessage());
            throw new RuntimeException("Error updating student", e);
        }
    }

    /**
     * Deletes a student by ID.
     */
    @Override
    public boolean delete(int id) {

        String sql = "DELETE FROM student WHERE id = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting student: " + e.getMessage());
            throw new RuntimeException("Error deleting student", e);
        }

    }

    /**
     * Maps a ResultSet row to a Student object.
     * Keeps mapping logic in one place.
     */
    private Student mapRowToStudent(ResultSet rs) throws SQLException {

        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("class_group"),
                rs.getTimestamp("create_date").toLocalDateTime()
        );
    }
}
