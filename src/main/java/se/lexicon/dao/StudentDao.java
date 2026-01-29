package se.lexicon.dao;

import se.lexicon.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    Student save(Student student); // Create or Update
    List<Student> findAll(); // Read all
    Optional<Student> findById(int id); // Read by ID
    void update(Student student); // Update existing
    boolean delete(int id); // Delete
}
