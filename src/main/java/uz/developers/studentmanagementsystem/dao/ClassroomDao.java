package uz.developers.studentmanagementsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.developers.studentmanagementsystem.entity.Classroom;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClassroomDao {
    private final Connection connection;

    CallableStatement callableStatement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @Autowired
    public ClassroomDao(ConnectionDao connectionDao) {
        this.connection = connectionDao.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish connection to the database.");
        }
    }

    //create
    public Result addClassroom(Classroom classroom) {
        int count = 0;
        try {
            //check name
            String checkNameQuery = "select count(*) from subject where name = ?";
            preparedStatement = this.connection.prepareStatement(checkNameQuery);
            preparedStatement.setString(1, classroom.getName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count > 0) {
                return new Result("Such classroom already exist", false);
            }

            //add new classroom
            String sqlQuery = "insert into classroom(name, capacity, photo) values (?,?,?)";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, classroom.getName());
            preparedStatement.setInt(2, classroom.getCapacity());
            preparedStatement.setString(3, classroom.getPhoto());
            preparedStatement.executeUpdate();
            return new Result("Successfully added", true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result("Error in server", false);
        }
    }

    //read
    public List<Classroom> getClassrooms() {
        List<Classroom> classrooms = new ArrayList<>();
        try {
            String selectQuery = "select * from classroom;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int capacity = resultSet.getInt("capacity");
                String photo = resultSet.getString("photo");
                classrooms.add(new Classroom(id, name, capacity, photo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classrooms;
    }

    //read/{id}
    public Classroom getClassroomById(Long id) {
        Classroom classroom = null;
        try {
            String selectQuery = "select * from classroom where id = ?;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int capacity = resultSet.getInt("capacity");
                String photo = resultSet.getString("photo");
                classroom = new Classroom(id, name, capacity, photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classroom;
    }

    //get next classroom by id
    public Classroom getNextClassroom(Long currentClassroomId) {
        Classroom nextClassroom = null;
        try {
            String selectQuery = "select * from classroom where id > ? order by id asc limit 1;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, currentClassroomId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int capacity = resultSet.getInt("capacity");
                String photo = resultSet.getString("photo");
                nextClassroom = new Classroom(id, name, capacity, photo);
            } else {
                String firstClassroomQuery = "select * from classroom order by id asc limit 1;";
                preparedStatement = this.connection.prepareStatement(firstClassroomQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    int capacity = resultSet.getInt("capacity");
                    String photo = resultSet.getString("photo");
                    nextClassroom = new Classroom(id, name, capacity, photo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextClassroom;
    }

    //update
    public boolean editClassroom(Classroom classroom) {
        boolean rowUpdated = false;
        try {
            String query = "update classroom set name = ?, capacity = ?, photo = ? where id = ?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, classroom.getName());
            preparedStatement.setInt(2, classroom.getCapacity());
            preparedStatement.setString(3, classroom.getPhoto());
            preparedStatement.setLong(4, classroom.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    //delete
    public void deleteClassroom(int id) {
        try {
            String deleteQuery = "delete from classroom where id =?";
            preparedStatement = this.connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Classroom is deleted");
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting classroom", e);
        }
    }
}
