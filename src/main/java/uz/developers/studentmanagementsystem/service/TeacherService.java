package uz.developers.studentmanagementsystem.service;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Teacher;

import java.util.List;

public interface TeacherService {


    List<Teacher> getAllTeachers(int size, int page);

    int getTotalTeachers();

    Teacher getTeacherById(Long id);

    Teacher getNextTeacher(Long currentTeacherId);

    Result addTeacher(Teacher teacher,String subjectName);

    boolean editTeacher(Teacher teacher);

    void deleteTeacher(int id);



}
