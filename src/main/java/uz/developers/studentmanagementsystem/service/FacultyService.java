package uz.developers.studentmanagementsystem.service;

import uz.developers.studentmanagementsystem.entity.Enrollment;
import uz.developers.studentmanagementsystem.entity.Faculty;
import uz.developers.studentmanagementsystem.entity.Result;


import java.util.List;

public interface FacultyService {


    List<Faculty> getAllFaculties(int size, int page);

    int getTotalFaculties();

    Faculty getFacultyById(Long id);

    Faculty getNextFaculty(Long currentFacultyId);

    Result addFaculty(Faculty faculty);

    boolean editFaculty(Faculty faculty);

    void deleteFaculty(int id);


}
