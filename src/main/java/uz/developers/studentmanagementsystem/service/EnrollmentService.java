package uz.developers.studentmanagementsystem.service;

import uz.developers.studentmanagementsystem.entity.Classroom;
import uz.developers.studentmanagementsystem.entity.Enrollment;
import uz.developers.studentmanagementsystem.entity.Result;


import java.util.List;

public interface EnrollmentService {


    //List<Enrollment> getAllEnrollments(int size, int page);

    int getTotalEnrollments();

    Enrollment getEnrollmentById(Long id);

    Enrollment getNextEnrollment(Long currentEnrollmentId);

    Result addEnrollment(Enrollment enrollment);

    boolean editEnrollment(Enrollment enrollment);

    void deleteEnrollment(int id);


}
