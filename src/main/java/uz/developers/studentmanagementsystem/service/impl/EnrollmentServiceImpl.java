package uz.developers.studentmanagementsystem.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.studentmanagementsystem.dao.EnrollmentDao;
import uz.developers.studentmanagementsystem.entity.Enrollment;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.service.EnrollmentService;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {


    private final EnrollmentDao enrollmentDao;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentDao enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }



    @Override
    public int getTotalEnrollments() {
        return enrollmentDao.getTotalEnrollments();
    }

    @Override
    public Enrollment getEnrollmentById(Long id) {
        return null;
    }

    @Override
    public Enrollment getNextEnrollment(Long currentEnrollmentId) {
        return null;
    }

    @Override
    public Result addEnrollment(Enrollment enrollment) {
        return null;
    }

    @Override
    public boolean editEnrollment(Enrollment enrollment) {
        return false;
    }

    @Override
    public void deleteEnrollment(int id) {

    }
}
