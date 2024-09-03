package uz.developers.studentmanagementsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Student;
import uz.developers.studentmanagementsystem.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDao {
    private final Connection connection;

    CallableStatement callableStatement;


    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @Autowired
    public StudentDao(ConnectionDao connectionDao) {
        this.connection = connectionDao.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish connection to the database.");
        }
    }


    //create
    public Result addStudent(Student student, String facultyName) {
        int count = 0;
        try {
            String checkEmailQuery = "select count(*) from student where email='" + student.getEmail() + "'";
            preparedStatement = this.connection.prepareStatement(checkEmailQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count > 0) {
                return new Result("Email already exist", false);
            }

            Long facultyId = getFacultyIdByName(facultyName);
            if (facultyId != null) {
                String sqlQuery = "insert into student(firstname, lastname, email, gender, password, photo, faculty_id) values (?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, student.getFirstname());
                preparedStatement.setString(2, student.getLastname());
                preparedStatement.setString(3, student.getEmail());
                preparedStatement.setString(4, student.getGender());
                preparedStatement.setString(5, student.getPassword());
                preparedStatement.setString(6, student.getPhoto());
                preparedStatement.setLong(7, facultyId);
                preparedStatement.executeUpdate();
                return new Result("Successfully added", true);
            } else {
                return new Result("Student not found!", false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result("Error in server", false);
        }
    }

    //faculty name by faculty_id for add student
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

    //read

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            String selectQuery = "select s.id, s.firstname, s.lastname, s.gender, s.photo, " +
                    "f.name as facultyName from student s inner join faculty f on s.faculty_id = f.id;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String gender = resultSet.getString("gender");
                String photo = resultSet.getString("photo");
                String facultyName = resultSet.getString("facultyName");
                students.add(new Student(id, firstname, lastname, gender, photo, facultyName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }


    //read/{id}

    public Student getStudentById(Long id) {
        Student student = null;
        try {
            String selectQuery = "select s.id, s.firstname, s.lastname, s.email, s.gender, s.password, s.photo," +
                    "f.name as facultyName from student s inner join faculty f on s.faculty_id = f.id where s.id = ?;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String password = resultSet.getString("password");
                String photo = resultSet.getString("photo");
                String facultyName = resultSet.getString("facultyName");
                student = new Student(id, firstname, lastname, email, gender, password, photo, facultyName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    //get next student by id

    public Student getNextStudent(Long currentStudentId) {
        Student nextStudent = null;
        try {
            String selectQuery = "select s.id, s.firstname, s.lastname, s.email, s.gender, s.password, s.photo, f.name as facultyName " +
                    "from student s inner join faculty f on s.faculty_id = f.id where s.id > ? order by id asc limit 1;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, currentStudentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String password = resultSet.getString("password");
                String photo = resultSet.getString("photo");
                String facultyName = resultSet.getString("facultyName");
                nextStudent = new Student(id, firstname, lastname, email, gender, password, photo, facultyName);
            } else {
                String firstStudentQuery = "select s.id, s.firstname, s.lastname, s.email, s.gender, s.password, s.photo, f.name as facultyName " +
                                           "from student s inner join faculty f on s.faculty_id = f.id order by id asc limit 1;";
                preparedStatement = this.connection.prepareStatement(firstStudentQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String email = resultSet.getString("email");
                    String gender = resultSet.getString("gender");
                    String password = resultSet.getString("password");
                    String photo = resultSet.getString("photo");
                    String facultyName = resultSet.getString("facultyName");
                    nextStudent = new Student(id, firstname, lastname, email, gender, password, photo, facultyName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextStudent;
    }

    //update

    public boolean editStudent(Student student) {
        boolean rowUpdated = false;
        try {
            String query = "update student set firstname = ?, lastname = ?, email = ?, gender = ?, password = ?, photo = ? where id = ?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, student.getFirstname());
            preparedStatement.setString(2, student.getLastname());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getGender());
            preparedStatement.setString(5, student.getPassword());
            preparedStatement.setString(6, student.getPhoto());
            preparedStatement.setLong(7, student.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    //delete

    public void deleteStudent(int id) {
        try {
            String deleteQuery = "delete from student where id =?";
            preparedStatement = this.connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Student is deleted");
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting student", e);
        }
    }


    //login

//    public Student studentLogin(String email, String password) {
//        Student student = null;
//        try {
//            String query = "select * from student where email=? and password=?";
//            preparedStatement = this.connection.prepareStatement(query);
//            preparedStatement.setString(1, email);
//            preparedStatement.setString(2, password);
//            resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                Long id = resultSet.getLong(1);
//                String firstname = resultSet.getString(2);
//                String lastname = resultSet.getString(3);
//                email = resultSet.getString(4);
//                String gender = resultSet.getString(5);
//                password = resultSet.getString(6);
//                String photo = resultSet.getString(7);
//                Long faculty_id = resultSet.getLong(7);
//                student = new Student(id, firstname, lastname, email, gender, password, photo, faculty_id);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.print(e.getMessage());
//        }
//        return student;
//    }


}
