package uz.developers.studentmanagementsystem.service;

import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Student;
import uz.developers.studentmanagementsystem.entity.Subject;

import java.util.List;

public interface SubjectService {


    List<Subject> getAllSubjects(int pageNumber, int pageSize);

    int getTotalSubjects();

    Subject getSubjectById(Long id);

    Subject getNextSubject(Long currentSubjectId);

    Result addSubject(Subject subject);

    boolean editSubject(Subject subject);

    void deleteSubject(int id);


}
