package se.lexicon.model;

import java.time.LocalDateTime;

public class Attendance {

    /*
    id int AI PK
    student_id int
    attendance_date date
    status enum('Present','Absent')
     */
    private int id;
    private Student student;
    private LocalDateTime attendanceDate;
    private Status status;

    public Attendance(Student student, LocalDateTime attendanceDate, Status status) {
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public Attendance(int id, Student student, Status status, LocalDateTime attendanceDate) {
        this.id = id;
        this.student = student;
        this.status = status;
        this.attendanceDate = attendanceDate;
    }

    public Attendance(Student student, Status status) {
        this.student = student;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDateTime attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", student=" + student +
                ", attendanceDate=" + attendanceDate +
                ", status=" + status +
                '}';
    }
}
