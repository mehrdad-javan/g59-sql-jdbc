package se.lexicon;

import se.lexicon.dao.StudentDao;
import se.lexicon.dao.StudentDaoImpl;
import se.lexicon.db.DatabaseConnection;
import se.lexicon.model.Student;

import java.sql.Connection;
import java.sql.SQLException;

public class AttendanceApp {

    void main() {
        try {
            Connection mySQLConnection = DatabaseConnection.getMySQLConnection();

            StudentDao studentDao = new StudentDaoImpl(mySQLConnection);

            /*
            Student student1 = new Student("Ali Hasan", "G1");
            student1 = studentDao.save(student1);
            IO.println("Student saved successfully: " + student1);
            */

            studentDao.findAll().forEach(IO::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
