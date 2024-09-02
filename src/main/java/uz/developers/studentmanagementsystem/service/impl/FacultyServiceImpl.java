package uz.developers.studentmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.studentmanagementsystem.dao.FacultyDao;
import uz.developers.studentmanagementsystem.entity.Faculty;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.service.FacultyService;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {


    private final FacultyDao facultyDao;

    @Autowired
    public FacultyServiceImpl(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return facultyDao.getFaculties();
    }

    @Override
    public Faculty getFacultyById(Long id) {
        return facultyDao.getFacultyById(id);
    }

    @Override
    public Faculty getNextFaculty(Long currentFacultyId) {
        return facultyDao.getNextFaculty(currentFacultyId);
    }

    @Override
    public Result addFaculty(Faculty faculty) {
        return facultyDao.addFaculty(faculty);
    }

    @Override
    public boolean editFaculty(Faculty faculty) {
        return facultyDao.editFaculty(faculty);
    }

    @Override
    public void deleteFaculty(int id) {
        facultyDao.deleteFaculty(id);
    }
}
