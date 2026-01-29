package se.lexicon.dao;

import se.lexicon.model.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceDao {
    Attendance save(Attendance attendance); // Create or Update
    List<Attendance> findAll(); // Read all
    Optional<Attendance> findById(int id); // Read by ID
    void update(Attendance attendance);
    boolean delete(int id);
}
