package uz.developers.studentmanagementsystem.service;

import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student getNextStudent(Long currentStudentId);

    Result addStudent(Student student);

    boolean editStudent(Student student);

    void deleteStudent(int id);
}
