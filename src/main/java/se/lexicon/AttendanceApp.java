package se.lexicon;

import se.lexicon.dao.AttendanceDao;
import se.lexicon.dao.AttendanceDaoImpl;
import se.lexicon.dao.StudentDao;
import se.lexicon.dao.StudentDaoImpl;
import se.lexicon.db.DatabaseConnection;
import se.lexicon.model.Attendance;
import se.lexicon.model.AttendanceStatus;
import se.lexicon.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class AttendanceApp {

    void main() {
       /* try {
            Connection mySQLConnection = DatabaseConnection.getMySQLConnection();

            StudentDao studentDao = new StudentDaoImpl(mySQLConnection);


            //Student student1 = new Student("Ali Hasan", "G1");
            //student1 = studentDao.save(student1);
            //IO.println("Student saved successfully: " + student1);


            studentDao.findAll().forEach(IO::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        try {
            Connection mySQLConnection = DatabaseConnection.getMySQLConnection();

            //
            try {
                mySQLConnection.setAutoCommit(false);

                StudentDao studentDao = new StudentDaoImpl(mySQLConnection);
                AttendanceDao attendanceDao = new AttendanceDaoImpl(mySQLConnection);

                Student student2 = new Student("Transaction Student", "G1");
                student2 = studentDao.save(student2);

                //
                Attendance attendance = new Attendance(student2, LocalDate.now(), AttendanceStatus.PRESENT);
                //student2.setId(10000); // ### simulate fk constraint violation to test rollback
                attendanceDao.save(attendance);

                mySQLConnection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                mySQLConnection.rollback();
            }

        } catch (SQLException e) {
            IO.println("Error: Database connection failed!");
            e.printStackTrace();
        }


    }
}
