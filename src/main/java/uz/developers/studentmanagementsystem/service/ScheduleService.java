package uz.developers.studentmanagementsystem.service;

import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Schedule;


import java.util.List;

public interface ScheduleService {


    List<Schedule> getAllSchedules();

    Schedule getScheduleById(Long id);

    Schedule getNextSchedule(Long currentScheduleId);

    Result addSchedule(Schedule schedule, String subjectName, String teacherName, String classroomName);

    boolean editSchedule(Schedule schedule);

    void deleteSchedule(int id);


}
