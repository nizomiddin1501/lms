package uz.developers.studentmanagementsystem.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.studentmanagementsystem.dao.ScheduleDao;
import uz.developers.studentmanagementsystem.entity.Result;
import uz.developers.studentmanagementsystem.entity.Schedule;
import uz.developers.studentmanagementsystem.service.ScheduleService;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {


    private final ScheduleDao scheduleDao;

    @Autowired
    public ScheduleServiceImpl(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleDao.getSchedules();
    }

    @Override
    public Schedule getScheduleById(Long id) {
        return scheduleDao.getScheduleById(id);
    }

    @Override
    public Schedule getNextSchedule(Long currentScheduleId) {
        return scheduleDao.getNextSchedule(currentScheduleId);
    }

    @Override
    public Result addSchedule(Schedule schedule, String subjectName, String teacherName, String classroomName) {
        return scheduleDao.addSchedule(schedule, subjectName, teacherName, classroomName);
    }

    @Override
    public boolean editSchedule(Schedule schedule) {
        return scheduleDao.editSchedule(schedule);
    }

    @Override
    public void deleteSchedule(int id) {
        scheduleDao.deleteSchedule(id);
    }
}
