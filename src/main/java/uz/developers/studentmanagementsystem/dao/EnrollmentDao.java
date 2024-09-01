package uz.developers.studentmanagementsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnrollmentDao {

    private final Connection connection;

    CallableStatement callableStatement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @Autowired
    public EnrollmentDao(ConnectionDao connectionDao) {
        this.connection = connectionDao.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish connection to the database.");
        }
    }


    //create
    public Result addTeacher(Teacher teacher, String subjectName) {
        int count = 0;
        try {
            String checkEmailQuery = "select count(*) from teacher where email='" + teacher.getEmail() + "'";
            preparedStatement = this.connection.prepareStatement(checkEmailQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count > 0) {
                return new Result("Email already exist", false);
            }
            Long subjectId = getSubjectIdByName(subjectName);

            if (subjectId != null) {
                String sqlQuery = "insert into teacher(firstname, lastname, email, gender, password, photo, subject_id) values (?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, teacher.getFirstname());
                preparedStatement.setString(2, teacher.getLastname());
                preparedStatement.setString(3, teacher.getEmail());
                preparedStatement.setString(4, teacher.getGender());
                preparedStatement.setString(5, teacher.getPassword());
                preparedStatement.setString(6, teacher.getPhoto());
                preparedStatement.setLong(7,subjectId);
                preparedStatement.executeUpdate();
                return new Result("Successfully added", true);
            } else {
                return new Result("Subject not found!", false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result("Error in server", false);
        }
    }


    //subject name by subject_id for add teacher

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

    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            String selectQuery = "select teacher.id, teacher.firstname, teacher.lastname, teacher.email, teacher.gender, teacher.photo, subject.name as subjectName " +
                    "from teacher inner join subject on subject.id = teacher.subject_id;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String photo = resultSet.getString("photo");
                String subjectName = resultSet.getString("subjectName");
                teachers.add(new Teacher(id, firstname, lastname, email, gender, photo,subjectName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }

    //read/{id}

    public Teacher getTeacherById(Long id) {
        Teacher teacher = null;
        try {
            String selectQuery = "select t.id, t.firstname, t.lastname, t.email, t.gender, t.password, t.photo, subject.name as subjectName" +
                    " from teacher as t inner join subject on subject.id = t.subject_id where t.id = ?;";
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
                String subjectName = resultSet.getString("subjectName");
                teacher = new Teacher(id, firstname, lastname, email, gender, password, photo, subjectName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacher;
    }

    //get next student by id

    public Teacher getNextTeacher(Long currentTeacherId) {
        Teacher nextTeacher = null;
        try {
            String selectQuery = "select t.id, t.firstname, t.lastname, t.email, t.gender, t.password, t.photo, \n" +
                    "subject.name as subjectName from teacher as t inner join \n" +
                    "subject on subject.id = t.subject_id where id > ? order by id asc limit 1;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, currentTeacherId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String password = resultSet.getString("password");
                String photo = resultSet.getString("photo");
                String subjectName = resultSet.getString("subjectName");
                nextTeacher = new Teacher(id, firstname, lastname, email, gender, password, photo, subjectName);
            } else {
                String firstTeacherQuery = "select t.id, t.firstname, t.lastname, t.email, t.gender, t.password, t.photo, \n" +
                        "subject.name as subjectName from teacher as t inner join \n" +
                        "subject on subject.id = t.subject_id order by id asc limit 1;";
                preparedStatement = this.connection.prepareStatement(firstTeacherQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String email = resultSet.getString("email");
                    String gender = resultSet.getString("gender");
                    String password = resultSet.getString("password");
                    String photo = resultSet.getString("photo");
                    String subjectName = resultSet.getString("subjectName");
                    nextTeacher = new Teacher(id, firstname, lastname, email, gender, password, photo, subjectName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextTeacher;
    }

    //update

    public boolean editTeacher(Teacher teacher) {
        boolean rowUpdated = false;
        try {
            String query = "update teacher set firstname = ?, lastname = ?, email = ?, gender = ?, password = ?, photo = ? where id = ?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, teacher.getFirstname());
            preparedStatement.setString(2, teacher.getLastname());
            preparedStatement.setString(3, teacher.getEmail());
            preparedStatement.setString(4, teacher.getGender());
            preparedStatement.setString(5, teacher.getPassword());
            preparedStatement.setString(6, teacher.getPhoto());
            preparedStatement.setLong(7, teacher.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    //delete

    public void deleteTeacher(int id) {
        try {
            String deleteQuery = "delete from teacher where id =?";
            preparedStatement = this.connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Teacher is deleted");
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting teacher", e);
        }
    }


}
