package se.lexicon.dao;

import se.lexicon.model.Attendance;

public interface AttendanceDao {
    Attendance save(Attendance attendance);
    Attendance findAll();

    Attendance findById(int id);
    void update(Attendance attendance);
    void delete(Attendance attendance);
}
