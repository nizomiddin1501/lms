package uz.developers.studentmanagementsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Student;
import uz.developers.studentmanagementsystem.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubjectDao {
    private final Connection connection;

    CallableStatement callableStatement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @Autowired
    public SubjectDao(ConnectionDao connectionDao) {
        this.connection = connectionDao.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish connection to the database.");
        }
    }




    //create
    public Result addSubject(Subject subject) {
        int count = 0;
        String subjectName = null;
        try {
            if (subject.getName() != null && !subject.getName().isEmpty()) {
                subjectName = subject.getName().substring(0, 1).toUpperCase() + subject.getName().substring(1).toLowerCase();
            } else {
                return new Result("Subject name cannot be empty", false);
            }

            //check name
            String checkNameQuery = "select count(*) from subject where name = ?";
            preparedStatement = this.connection.prepareStatement(checkNameQuery);
            preparedStatement.setString(1,subjectName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count > 0) {
                return new Result("Such subject already exist", false);
            }

            //add new subject
            String sqlQuery = "insert into subject(name, photo, profession) values (?,?,?)";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, subjectName);
            preparedStatement.setString(2, subject.getPhoto());
            preparedStatement.setString(3, subject.getProfession());
            preparedStatement.executeUpdate();
            return new Result("Successfully added", true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result("Error in server", false);
        }
    }

    //read

    public List<Subject> getSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try {
            String selectQuery = "select * from subject;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String photo = resultSet.getString("photo");
                String profession = resultSet.getString("profession");
                subjects.add(new Subject(id, name, photo, profession));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }

    //read/{id}

    public Subject getSubjectById(Long id) {
        Subject subject = null;
        try {
            String selectQuery = "select * from subject where id = ?;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String photo = resultSet.getString("photo");
                String profession = resultSet.getString("profession");
                subject = new Subject(id, name, photo, profession);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

    //get next subject by id

    public Subject getNextSubject(Long currentSubjectId) {
        Subject nextSubject = null;
        try {
            String selectQuery = "select * from subject where id > ? order by id asc limit 1;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, currentSubjectId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String photo = resultSet.getString("photo");
                String profession = resultSet.getString("profession");
                nextSubject = new Subject(id, name, photo, profession);
            } else {
                String firstSubjectQuery = "select * from subject order by id asc limit 1;";
                preparedStatement = this.connection.prepareStatement(firstSubjectQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String photo = resultSet.getString("photo");
                    String profession = resultSet.getString("profession");
                    nextSubject = new Subject(id, name, photo, profession);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextSubject;
    }

    //update

    public boolean editSubject(Subject subject) {
        boolean rowUpdated = false;
        try {
            String query = "update subject set name = ?, photo = ?, profession = ? where id = ?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, subject.getName());
            preparedStatement.setString(2, subject.getPhoto());
            preparedStatement.setString(3, subject.getProfession());
            preparedStatement.setLong(4, subject.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    //delete

    public void deleteSubject(int id) {
        try {
            String deleteQuery = "delete from subject where id =?";
            preparedStatement = this.connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Subject is deleted");
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting subject", e);
        }
    }













}
