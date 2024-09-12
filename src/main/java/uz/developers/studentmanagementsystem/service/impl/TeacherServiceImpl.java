package uz.developers.studentmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.studentmanagementsystem.dao.StudentDao;
import uz.developers.studentmanagementsystem.dao.TeacherDao;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Teacher;
import uz.developers.studentmanagementsystem.service.TeacherService;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {


    private final TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }


    @Override
    public List<Teacher> getAllTeachers(int size, int page) {
        return teacherDao.getTeachers(size,page);
    }

    @Override
    public int getTotalTeachers() {
        return teacherDao.getTotalTeachers();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherDao.getTeacherById(id);
    }

    @Override
    public Teacher getNextTeacher(Long currentTeacherId) {
        return teacherDao.getNextTeacher(currentTeacherId);
    }

    @Override
    public Result addTeacher(Teacher teacher, String subjectName) {
        return teacherDao.addTeacher(teacher, subjectName);
    }

    @Override
    public boolean editTeacher(Teacher teacher) {
        return teacherDao.editTeacher(teacher);
    }

    @Override
    public void deleteTeacher(int id) {
        teacherDao.deleteTeacher(id);
    }
}
