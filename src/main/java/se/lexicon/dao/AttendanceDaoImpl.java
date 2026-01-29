package se.lexicon.dao;


import se.lexicon.model.Attendance;
import se.lexicon.model.AttendanceStatus;
import se.lexicon.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of AttendanceDao.
 *
 * Demonstrates:
 * - Using DataSource for connection management
 * - JOIN queries
 * - Enum mapping
 * - Proper resource handling
 */
public class AttendanceDaoImpl implements AttendanceDao {

    private final Connection connection;

    /**
     * Connection is injected.
     * The DAO does not manage connection creation.
     */
    public AttendanceDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Saves a new attendance record.
     * The database generates the ID automatically.
     */
    @Override
    public Attendance save(Attendance attendance) {

        String sql = """
            INSERT INTO attendance (student_id, attendance_date, status)
            VALUES (?, ?, ?)
            """;

        try (
                PreparedStatement ps =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, attendance.getStudent().getId());
            ps.setDate(2, Date.valueOf(attendance.getAttendanceDate()));
            ps.setString(3, attendance.getStatus().name());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    attendance.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error saving attendance: " + e.getMessage());
            throw new RuntimeException("Error saving attendance", e);
        }

        return attendance;
    }

    /**
     * Retrieves all attendance records with student information.
     */
    @Override
    public List<Attendance> findAll() {

        List<Attendance> attendances = new ArrayList<>();

        String sql = """
            SELECT a.id, a.attendance_date, a.status,
                   s.id AS student_id, s.name, s.class_group, s.create_date
            FROM attendance a
            JOIN student s ON a.student_id = s.id
            """;

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                attendances.add(mapRowToAttendance(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error retrieving attendance records: " + e.getMessage());
            throw new RuntimeException("Error retrieving attendance records", e);
        }

        return attendances;
    }

    /**
     * Retrieves an attendance record by ID.
     */
    @Override
    public Optional<Attendance> findById(int id) {

        String sql = """
            SELECT a.id, a.attendance_date, a.status,
                   s.id AS student_id, s.name, s.class_group, s.create_date
            FROM attendance a
            JOIN student s ON a.student_id = s.id
            WHERE a.id = ?
            """;

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToAttendance(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error retrieving attendance: " + e.getMessage());
            throw new RuntimeException("Error retrieving attendance", e);
        }

        return Optional.empty();
    }

    /**
     * Updates an existing attendance record.
     */
    @Override
    public void update(Attendance attendance) {

        String sql = """
            UPDATE attendance
            SET student_id = ?, attendance_date = ?, status = ?
            WHERE id = ?
            """;

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, attendance.getStudent().getId());
            ps.setDate(2, Date.valueOf(attendance.getAttendanceDate()));
            ps.setString(3, attendance.getStatus().name());
            ps.setInt(4, attendance.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("❌ Error updating attendance: " + e.getMessage());
            throw new RuntimeException("Error updating attendance", e);
        }
    }

    /**
     * Deletes an attendance record by ID.
     */
    @Override
    public boolean delete(int id) {

        String sql = "DELETE FROM attendance WHERE id = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting attendance: " + e.getMessage());
            throw new RuntimeException("Error deleting attendance", e);
        }

    }

    /**
     * Maps a ResultSet row to an Attendance object.
     * Keeps mapping logic in one place.
     */
    private Attendance mapRowToAttendance(ResultSet rs) throws SQLException {

        Student student = new Student(
                rs.getInt("student_id"),
                rs.getString("name"),
                rs.getString("class_group"),
                rs.getTimestamp("create_date").toLocalDateTime()
        );

        return new Attendance(
                rs.getInt("id"),
                student,
                rs.getDate("attendance_date").toLocalDate(),
                AttendanceStatus.valueOf(rs.getString("status"))
        );
    }
}
