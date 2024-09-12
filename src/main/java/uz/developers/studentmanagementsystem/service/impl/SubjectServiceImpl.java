package uz.developers.studentmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.studentmanagementsystem.dao.StudentDao;
import uz.developers.studentmanagementsystem.dao.SubjectDao;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Student;
import uz.developers.studentmanagementsystem.entity.Subject;
import uz.developers.studentmanagementsystem.service.SubjectService;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {


    private final SubjectDao subjectDao;

    @Autowired
    public SubjectServiceImpl(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    @Override
    public List<Subject> getAllSubjects(int pageNumber, int pageSize) {
        return subjectDao.getSubjects(pageNumber,pageSize);
    }

    @Override
    public int getTotalSubjects() {
        return subjectDao.getTotalSubjects();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectDao.getSubjectById(id);
    }

    @Override
    public Subject getNextSubject(Long currentSubjectId) {
        return subjectDao.getNextSubject(currentSubjectId);
    }

    @Override
    public Result addSubject(Subject subject) {
        return subjectDao.addSubject(subject);
    }

    @Override
    public boolean editSubject(Subject subject) {
        return subjectDao.editSubject(subject);
    }

    @Override
    public void deleteSubject(int id) {
        subjectDao.deleteSubject(id);
    }
}
