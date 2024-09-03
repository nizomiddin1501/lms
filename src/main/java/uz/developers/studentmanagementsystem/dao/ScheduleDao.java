package uz.developers.studentmanagementsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Schedule;
import uz.developers.studentmanagementsystem.entity.Teacher;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleDao {

    private final Connection connection;

    CallableStatement callableStatement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @Autowired
    public ScheduleDao(ConnectionDao connectionDao) {
        this.connection = connectionDao.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish connection to the database.");
        }
    }


    //create
    public Result addSchedule(Schedule schedule, String subjectName, String teacherName, String classroomName) {
        try {
            Long subjectId = getSubjectIdByName(subjectName);
            Long teacherId = getTeacherIdByName(teacherName);
            Long classroomId = getClassroomIdByName(classroomName);

            if (subjectId != null && teacherId != null && classroomId != null) {
                String sqlQuery = "insert into schedule(subject_id, teacher_id, classroom_id, day_of_week, start_time, end_time) values (?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setLong(1,subjectId);
                preparedStatement.setLong(2, teacherId);
                preparedStatement.setLong(3, classroomId);
                preparedStatement.setString(4, schedule.getDayOfWeek());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(schedule.getStartTime()));
                preparedStatement.setTimestamp(6, Timestamp.valueOf(schedule.getStartTime()));
                preparedStatement.executeUpdate();
                return new Result("Successfully added", true);
            } else {
                return new Result("Subject or Teacher or Classroom not found!", false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result("Error in server", false);
        }
    }


    //subject name by subject_id for add schedule
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

    //teacher name by teacher_id for add schedule
    public Long getTeacherIdByName(String teacherName) {
        Long teacherId = null;
        // teacherName dan firstname va lastname ni ajratish
        String[] nameParts = teacherName.split(" ");
        if (nameParts.length < 2) {
            throw new IllegalArgumentException("Teacher name should contain both first name and last name.");
        }
        String firstname = nameParts[0].substring(0, 1).toUpperCase() + nameParts[0].substring(1).toLowerCase();
        String lastname = nameParts[1].substring(0, 1).toUpperCase() + nameParts[1].substring(1).toLowerCase();
        //teacherName = teacherName.substring(0, 1).toUpperCase() + teacherName.substring(1).toLowerCase();
        try {
            String selectQuery = "select id from teacher where firstname = ? and lastname = ?;";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacherId = resultSet.getLong("id");
            }else {
                System.out.println("No teacher found with the name: " + teacherName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacherId;
    }

    //classroom name by classroom_id for add schedule
    public Long getClassroomIdByName(String classroomName) {
        Long classroomId = null;
        classroomName = classroomName.substring(0, 1).toUpperCase() + classroomName.substring(1).toLowerCase();
        try {
            String selectQuery = "select id from classroom where name = ?;";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, classroomName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classroomId = resultSet.getLong("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classroomId;
    }

    //read

    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        try {
            String selectQuery = "select sc.id, s.name as subjectName, t.firstname, t.lastname  , \n" +
                    "                   c.name as classroomName, sc.day_of_week,   \n" +
                    "                   sc.start_time, sc.end_time from schedule sc \n" +
                    "\t\t\t\t   inner join subject s on sc.subject_id = s.id\n" +
                    "\t\t\t\t   inner join teacher t on sc.teacher_id = t.id\n" +
                    "\t\t\t\t   inner join classroom c on sc.classroom_id = c.id;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String subjectName = resultSet.getString("subjectName");
                String teacherFirstname = resultSet.getString("firstname");
                String teacherLastname = resultSet.getString("lastname");
                String classroomName = resultSet.getString("classroomName");
                String dayOfWeek = resultSet.getString("day_of_week");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();

                // To'liq ismni birlashtirish
                String teacherName = teacherFirstname + " " + teacherLastname;

                schedules.add(new Schedule(id, subjectName, teacherName, classroomName, dayOfWeek, startTime, endTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;
    }

    //read/{id}

    public Schedule getScheduleById(Long id) {
        Schedule schedule = null;
        try {
            String selectQuery = "select sc.id, s.name as subjectName, t.firstname, t.lastname , \n" +
                    "                   c.name as classroomName, sc.day_of_week,   \n" +
                    "                   sc.start_time, sc.end_time from schedule sc \n" +
                    "\t\t\t\t   inner join subject s on sc.subject_id = s.id\n" +
                    "\t\t\t\t   inner join teacher t on sc.teacher_id = t.id\n" +
                    "\t\t\t\t   inner join classroom c on sc.classroom_id = c.id where sc.id = ?;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong("id");
                String subjectName = resultSet.getString("subjectName");
                String teacherFirstname = resultSet.getString("firstname");
                String teacherLastname = resultSet.getString("lastname");
                String classroomName = resultSet.getString("classroomName");
                String dayOfWeek = resultSet.getString("day_of_week");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();

                // To'liq ismni birlashtirish
                String teacherName = teacherFirstname + " " + teacherLastname;

                schedule = new Schedule(id, subjectName, teacherName, classroomName, dayOfWeek, startTime, endTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedule;
    }

    //get next schedule by id
    public Schedule getNextSchedule(Long currentScheduleId) {
        Schedule nextSchedule = null;
        try {
            String selectQuery = "select sc.id, s.name as subjectName, t.firstname, t.lastname , \n" +
                    "                   c.name as classroomName, sc.day_of_week,   \n" +
                    "                   sc.start_time, sc.end_time from schedule sc \n" +
                    "\t\t\t\t   inner join subject s on sc.subject_id = s.id\n" +
                    "\t\t\t\t   inner join teacher t on sc.teacher_id = t.id\n" +
                    "\t\t\t\t   inner join classroom c on sc.classroom_id = c.id where sc.id > ? order by id asc limit 1;";
            preparedStatement = this.connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, currentScheduleId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String subjectName = resultSet.getString("subjectName");
                String teacherFirstname = resultSet.getString("teacherFirstname");
                String teacherLastname = resultSet.getString("teacherLastname");
                String classroomName = resultSet.getString("classroomName");
                String dayOfWeek = resultSet.getString("dayOfWeek");
                LocalDateTime startTime = resultSet.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("endTime").toLocalDateTime();
                // To'liq ismni birlashtirish
                String teacherName = teacherFirstname + " " + teacherLastname;

                nextSchedule = new Schedule(id, subjectName, teacherName, classroomName, dayOfWeek, startTime, endTime);
            } else {
                String firstScheduleQuery = "select sc.id, s.name as subjectName, t.firstname, t.lastname , \n" +
                        "                   c.name as classroomName, sc.day_of_week,   \n" +
                        "                   sc.start_time, sc.end_time from schedule sc \n" +
                        "\t\t\t\t   inner join subject s on sc.subject_id = s.id\n" +
                        "\t\t\t\t   inner join teacher t on sc.teacher_id = t.id\n" +
                        "\t\t\t\t   inner join classroom c on sc.classroom_id = c.id order by sc.id asc limit 1;";
                preparedStatement = this.connection.prepareStatement(firstScheduleQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String subjectName = resultSet.getString("subjectName");
                    String teacherFirstname = resultSet.getString("teacherFirstname");
                    String teacherLastname = resultSet.getString("teacherLastname");
                    String classroomName = resultSet.getString("classroomName");
                    String dayOfWeek = resultSet.getString("dayOfWeek");
                    LocalDateTime startTime = resultSet.getTimestamp("startTime").toLocalDateTime();
                    LocalDateTime endTime = resultSet.getTimestamp("endTime").toLocalDateTime();

                    // To'liq ismni birlashtirish
                    String teacherName = teacherFirstname + " " + teacherLastname;

                    nextSchedule = new Schedule(id, subjectName, teacherName, classroomName, dayOfWeek, startTime, endTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextSchedule;
    }

    //update
    public boolean editSchedule(Schedule schedule) {
        boolean rowUpdated = false;
        try {

            // subjectId, teacherId va classroomId ni olish uchun nomlar bo'yicha qidirish

            Long subjectId = getSubjectIdByName(schedule.getSubjectName());
            Long teacherId = getTeacherIdByName(schedule.getTeacherName());
            Long classroomId = getClassroomIdByName(schedule.getClassroomName());

            String editQuery = "update schedule set subject_id = ?, teacher_id = ?, classroom_id = ?, day_of_week = ?, start_time = ?, end_time = ? where id = ?";
            preparedStatement = this.connection.prepareStatement(editQuery);
            preparedStatement.setLong(1, subjectId);
            preparedStatement.setLong(2, teacherId);
            preparedStatement.setLong(3, classroomId);
            preparedStatement.setString(4, schedule.getDayOfWeek());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(schedule.getStartTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(schedule.getEndTime()));
            preparedStatement.setLong(7, schedule.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    //delete

    public void deleteSchedule(int id) {
        try {
            String deleteQuery = "delete from schedule where id =?";
            preparedStatement = this.connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Schedule is deleted");
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting schedule", e);
        }
    }


}
