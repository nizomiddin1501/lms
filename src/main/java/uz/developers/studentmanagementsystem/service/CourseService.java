package uz.developers.studentmanagementsystem.service;
import uz.developers.studentmanagementsystem.entity.Course;
import uz.developers.studentmanagementsystem.entity.Result;

import java.util.List;

public interface CourseService {


    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course getNextCourse(Long currentCourseId);

    Result addCourse(Course course, String facultyName, String subjectName);

    boolean editCourse(Course course);

    void deleteCourse(int id);



}
