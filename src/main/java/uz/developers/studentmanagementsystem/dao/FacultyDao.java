package uz.developers.studentmanagementsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.developers.studentmanagementsystem.entity.Faculty;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FacultyDao {
    private final Connection connection;

    CallableStatement callableStatement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @Autowired
    public FacultyDao(ConnectionDao connectionDao) {
        this.connection = connectionDao.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish connection to the database.");
        }
    }


    //create
    public Result addFaculty(Faculty faculty) {
        int count = 0;
        String facultyName = null;
        try {
            if (faculty.getName() != null && !faculty.getName().isEmpty()) {
                facultyName = faculty.getName().substring(0, 1).toUpperCase() + faculty.getName().substring(1).toLowerCase();
            } else {
                return new Result("Faculty name cannot be empty", false);
            }
            //check name
            String checkNameQuery = "select count(*) from faculty where name = ?";
            preparedStatement = this.connection.prepareStatement(checkNameQuery);
            preparedStatement.setString(1, facultyName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count > 0) {
                return new Result("Such faculty already exist", false);
            }

            //add new faculty
            String sqlQuery = "insert into faculty(name, dean, phone, email, photo) values (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, facultyName);
            preparedStatement.setString(2, faculty.getDean());
            preparedStatement.setString(3, faculty.getPhone());
            preparedStatement.setString(4, faculty.getEmail());
            preparedStatement.setString(5, faculty.getPhoto());
            preparedStatement.executeUpdate();
            return new Result("Successfully added", true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result("Error in server", false);
        }
    }

    //read

    public List<Faculty> getFaculties() {
        List<Faculty> faculties = new ArrayList<>();
        try {
            String selectQuery = "select * from faculty;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String dean = resultSet.getString("dean");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String photo = resultSet.getString("photo");
                faculties.add(new Faculty(id, name, dean, phone, email, photo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculties;
    }

    //read/{id}

    public Faculty getFacultyById(Long id) {
        Faculty faculty = null;
        try {
            String selectQuery = "select * from faculty where id = ?;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String dean = resultSet.getString("dean");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String photo = resultSet.getString("photo");
                faculty = new Faculty(id, name, dean, phone, email, photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculty;
    }

    //get next faculty by id

    public Faculty getNextFaculty(Long currentFacultyId) {
        Faculty nextFaculty = null;
        try {
            String selectQuery = "select * from faculty where id > ? order by id asc limit 1;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, currentFacultyId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String dean = resultSet.getString("dean");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String photo = resultSet.getString("photo");
                nextFaculty = new Faculty(id, name, dean, phone, email, photo);
            } else {
                String firstFacultyQuery = "select * from faculty order by id asc limit 1;";
                preparedStatement = this.connection.prepareStatement(firstFacultyQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String dean = resultSet.getString("dean");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String photo = resultSet.getString("photo");
                    nextFaculty = new Faculty(id, name, dean, phone, email, photo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextFaculty;
    }

    //update

    public boolean editFaculty(Faculty faculty) {
        boolean rowUpdated = false;
        try {
            String query = "update faculty set name = ?, dean = ?, phone = ?, email = ?, photo = ? where id = ?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.setString(2, faculty.getDean());
            preparedStatement.setString(3, faculty.getPhone());
            preparedStatement.setString(4, faculty.getEmail());
            preparedStatement.setString(5, faculty.getPhoto());
            preparedStatement.setLong(6, faculty.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    //delete

    public void deleteFaculty(int id) {
        try {
            String deleteQuery = "delete from faculty where id =?";
            preparedStatement = this.connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Faculty is deleted");
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting faculty", e);
        }
    }


}
