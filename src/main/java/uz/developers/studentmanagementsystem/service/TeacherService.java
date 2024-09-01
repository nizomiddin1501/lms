package uz.developers.studentmanagementsystem.service;

import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Student;
import uz.developers.studentmanagementsystem.entity.Teacher;

import java.util.List;

public interface TeacherService {


    List<Teacher> getAllTeachers();

    Teacher getTeacherById(Long id);

    Teacher getNextTeacher(Long currentTeacherId);

    Result addTeacher(Teacher teacher,String subjectName);

    boolean editTeacher(Teacher teacher);

    void deleteTeacher(int id);



}
