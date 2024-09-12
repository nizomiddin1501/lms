package uz.developers.studentmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.studentmanagementsystem.dao.ClassroomDao;
import uz.developers.studentmanagementsystem.dao.SubjectDao;
import uz.developers.studentmanagementsystem.entity.Classroom;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.service.ClassroomService;


import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {


    private final ClassroomDao classroomDao;

    @Autowired
    public ClassroomServiceImpl(ClassroomDao classroomDao) {
        this.classroomDao = classroomDao;
    }


    @Override
    public List<Classroom> getAllClassrooms(int page, int size) {
        return classroomDao.getClassrooms(page,size);
    }

    @Override
    public int getTotalClassrooms() {
        return classroomDao.getTotalClassrooms();
    }

    @Override
    public Classroom getClassroomById(Long id) {
        return classroomDao.getClassroomById(id);
    }

    @Override
    public Classroom getNextClassroom(Long currentClassroomId) {
        return classroomDao.getNextClassroom(currentClassroomId);
    }

    @Override
    public Result addClassroom(Classroom classroom) {
        return classroomDao.addClassroom(classroom);
    }

    @Override
    public boolean editClassroom(Classroom classroom) {
        return classroomDao.editClassroom(classroom);
    }

    @Override
    public void deleteClassroom(int id) {
        classroomDao.deleteClassroom(id);
    }
}
