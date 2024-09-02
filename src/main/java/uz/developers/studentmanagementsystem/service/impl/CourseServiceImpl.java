package uz.developers.studentmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.studentmanagementsystem.dao.CourseDao;
import uz.developers.studentmanagementsystem.dao.SubjectDao;
import uz.developers.studentmanagementsystem.entity.Course;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Subject;
import uz.developers.studentmanagementsystem.service.CourseService;
import uz.developers.studentmanagementsystem.service.SubjectService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {


    private final CourseDao courseDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDao.getCourses();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseDao.getCourseById(id);
    }

    @Override
    public Course getNextCourse(Long currentCourseId) {
        return courseDao.getNextCourse(currentCourseId);
    }

    @Override
    public Result addCourse(Course teacher, String facultyName, String subjectName) {
        return courseDao.addCourse(teacher,facultyName,subjectName);
    }

    @Override
    public boolean editCourse(Course course) {
        return courseDao.editCourse(course);
    }

    @Override
    public void deleteCourse(int id) {
        courseDao.deleteCourse(id);
    }
}
