package uz.developers.studentmanagementsystem.service;

import uz.developers.studentmanagementsystem.entity.Enrollment;
import uz.developers.studentmanagementsystem.entity.Result;


import java.util.List;

public interface EnrollmentService {


    List<Enrollment> getAllEnrollments();

    Enrollment getEnrollmentById(Long id);

    Enrollment getNextEnrollment(Long currentEnrollmentId);

    Result addEnrollment(Enrollment enrollment);

    boolean editEnrollment(Enrollment enrollment);

    void deleteEnrollment(int id);


}
