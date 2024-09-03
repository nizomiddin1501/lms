package uz.developers.studentmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.studentmanagementsystem.dao.StudentDao;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Student;
import uz.developers.studentmanagementsystem.service.StudentService;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getStudents();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentDao.getStudentById(id);
    }


    @Override
    public Student getNextStudent(Long currentStudentId) {
        return studentDao.getNextStudent(currentStudentId);
    }

    @Override
    public Result addStudent(Student student, String facultyName) {
        return studentDao.addStudent(student, facultyName);
    }

    @Override
    public boolean editStudent(Student student) {
        return studentDao.editStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentDao.deleteStudent(id);
    }
}
