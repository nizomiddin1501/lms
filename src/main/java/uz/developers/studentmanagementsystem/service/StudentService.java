package uz.developers.studentmanagementsystem.service;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents(int size, int page);

    int getTotalStudents();

    Student getStudentById(Long id);

    Student getNextStudent(Long currentStudentId);

    Result addStudent(Student student, String facultyName);

    boolean editStudent(Student student);

    void deleteStudent(int id);
}
