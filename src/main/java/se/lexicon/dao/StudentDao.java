package se.lexicon.dao;

import se.lexicon.model.Student;

import java.util.List;

public interface StudentDao {

    Student save(Student student);

    List<Student> findAll();
}
