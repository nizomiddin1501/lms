package uz.developers.studentmanagementsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.developers.studentmanagementsystem.entity.Course;
import uz.developers.studentmanagementsystem.entity.Faculty;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseDao {

    private final Connection connection;

    CallableStatement callableStatement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @Autowired
    public CourseDao(ConnectionDao connectionDao) {
        this.connection = connectionDao.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish connection to the database.");
        }
    }


    //create
    public Result addCourse(Course course, String facultyName, String subjectName) {
        int count = 0;
        try {
            Long facultyId = getFacultyIdByName(facultyName);
            Long subjectId = getSubjectIdByName(subjectName);

            if (subjectId != null) {
                String sqlQuery = "insert into course(name, description, credits, faculty_id, subject_id, photo) values (?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, course.getName());
                preparedStatement.setString(2, course.getDescription());
                preparedStatement.setInt(3, course.getCredits());
                preparedStatement.setLong(4,facultyId);
                preparedStatement.setLong(5,subjectId);
                preparedStatement.setString(6,course.getPhoto());
                preparedStatement.executeUpdate();
                return new Result("Successfully added", true);
            } else {
                return new Result("Course not found!", false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result("Error in server", false);
        }
    }


    //faculty name by faculty_id for add course

    public Long getFacultyIdByName(String facultyName) {
        Long facultyId = null;
        facultyName = facultyName.substring(0, 1).toUpperCase() + facultyName.substring(1).toLowerCase();
        try {
            String selectQuery = "select id from faculty where name = ?;";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, facultyName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                facultyId = resultSet.getLong("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facultyId;
    }


    //subject name by subject_id for add course

    public Long getSubjectIdByName(String subjectName) {
        Long subjectId = null;
        subjectName = subjectName.substring(0, 1).toUpperCase() + subjectName.substring(1).toLowerCase();
        try {
            String selectQuery = "select id from subject where name = ?;";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, subjectName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subjectId = resultSet.getLong("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectId;
    }



    //read

    public List<Course> getCourses(int page, int size) {
        List<Course> courses = new ArrayList<>();
        try {
            String selectQuery = "select c.id, c.name, c.description, c.credits, f.name as facultyName, s.name as subjectName, c.photo " +
                    "from course as c inner join faculty f on c.faculty_id = f.id inner join subject s on c.subject_id = s.id limit ? offset ?;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1,size);
            preparedStatement.setInt(2, (page - 1) * size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int credits = resultSet.getInt("credits");
                String facultyName = resultSet.getString("facultyName");
                String subjectName = resultSet.getString("subjectName");
                String photo = resultSet.getString("photo");
                courses.add(new Course(id, name, description, credits, facultyName, subjectName, photo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }


    //find pages for pagination
    public int getTotalCourses() {
        int totalCourses = 0;
        try {
            String countQuery = "select count(*) from course;";

            preparedStatement = this.connection.prepareStatement(countQuery);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalCourses = resultSet.getInt(1); // Umumiy yozuvlar soni
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCourses;
    }

    //read/{id}

    public Course getCourseById(Long id) {
        Course course = null;
        try {
            String selectQuery = "select c.id, c.name, c.description, c.credits, f.name as facultyName, s.name as subjectName, c.photo " +
                    "from course c inner join faculty f on c.faculty_id = f.id inner join subject s on c.subject_id = s.id where c.id = ?;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int credits = resultSet.getInt("credits");
                String facultyName = resultSet.getString("facultyName");
                String subjectName = resultSet.getString("subjectName");
                String photo = resultSet.getString("photo");
                course = new Course(id, name, description, credits, facultyName, subjectName, photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }

    //get next course by id
    public Course getNextCourse(Long currentCourseId) {
        Course nextCourse = null;
        try {
            String selectQuery = "select c.id, c.name, c.description, c.credits, f.name as facultyName, " +
                    "s.name as subjectName, c.photo from course as c inner join faculty f on c.faculty_id = f.id \n" +
                    "inner join subject s on c.subject_id = s.id where id > ? order by c.id asc limit 1;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, currentCourseId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int credits = resultSet.getInt("credits");
                String facultyName = resultSet.getString("facultyName");
                String subjectName = resultSet.getString("subjectName");
                String photo = resultSet.getString("photo");
                nextCourse = new Course(id, name, description, credits, facultyName, subjectName, photo);
            } else {
                String firstCourseQuery = "select c.id, c.name, c.description, c.credits, f.name as facultyName, s.name " +
                        "as subjectName, c.photo from course as c inner join faculty f on c.faculty_id = f.id " +
                        "inner join subject s on c.subject_id = s.id order by c.id asc limit 1;";
                preparedStatement = this.connection.prepareStatement(firstCourseQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    int credits = resultSet.getInt("credits");
                    String facultyName = resultSet.getString("facultyName");
                    String subjectName = resultSet.getString("subjectName");
                    String photo = resultSet.getString("photo");
                    nextCourse = new Course(id, name, description, credits, facultyName, subjectName, photo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextCourse;
    }

    //update

    public boolean editCourse(Course course) {
        boolean rowUpdated = false;
        try {
            String query = "update course set name = ?, description = ?, credits = ? where id = ?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setInt(3, course.getCredits());
            preparedStatement.setLong(4, course.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    //delete

    public void deleteCourse(int id) {
        try {
            String deleteQuery = "delete from course where id =?";
            preparedStatement = this.connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Course is deleted");
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting course", e);
        }
    }


}
