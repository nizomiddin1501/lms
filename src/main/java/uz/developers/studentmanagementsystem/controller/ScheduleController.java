package uz.developers.studentmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.entity.Schedule;
import uz.developers.studentmanagementsystem.service.ScheduleService;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedules")
    public String getSchedules(Model model) {
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        return "schedules";
    }

    @GetMapping("/schedules/new")
    public String createScheduleForm(Model model) {
        Schedule schedule = new Schedule();
        model.addAttribute("schedule", schedule);
        return "create_schedule";
    }

    @PostMapping("/addSchedule")
    public String saveSchedule(@ModelAttribute("teacher") Schedule schedule,
                               @RequestParam("subjectName") String subjectName,
                               @RequestParam("teacherName") String teacherName,
                               @RequestParam("classroomName") String classroomname) {
        scheduleService.addSchedule(schedule, subjectName, teacherName, classroomname);
        return "redirect:/schedules";
    }


    @GetMapping("/schedules/edit/{id}")
    public String editScheduleForm(@PathVariable Long id, Model model) {
        model.addAttribute("schedule", scheduleService.getScheduleById(id));
        return "edit_schedule";
    }

    @PostMapping("/schedules/{id}")
    public String editSchedule(@PathVariable Long id,
                               @ModelAttribute("schedule") Schedule schedule,
                               Model model) {
        // get schedule from database by id
        Schedule scheduleById = scheduleService.getScheduleById(id);
        scheduleById.setId(id);
        scheduleById.setSubjectName(schedule.getSubjectName());
        scheduleById.setTeacherName(schedule.getTeacherName());
        scheduleById.setClassroomName(schedule.getClassroomName());
        scheduleById.setDayOfWeek(schedule.getDayOfWeek());
        scheduleById.setStartTime(schedule.getStartTime());
        scheduleById.setEndTime(schedule.getEndTime());

        //save update schedule object

        scheduleService.editSchedule(scheduleById);
        return "redirect:/schedules";

    }

    @GetMapping("/schedules/{id}")
    public String deleteSchedule(@PathVariable int id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules";

    }

    @GetMapping("/schedules/show/{id}")
    public String showSchedule(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.getScheduleById(id); // Schedule ni ID bo'yicha olish
        model.addAttribute("schedule", schedule); // Schedule ma'lumotlarini modelga qo'shish
        return "schedule_show"; // schedule_show.html sahifasini qaytarish

    }

    @GetMapping("/schedules/show/next/{currentId}")
    public String showNextSchedule(@PathVariable Long currentId, Model model) {
        Schedule nextSchedule = scheduleService.getNextSchedule(currentId);
        if (nextSchedule == null) {
            // Agar keyingi schedule mavjud bo'lmasa, qaytib keladi yoki oxirgi schedule ga o'tadi
            return "redirect:/schedules ";  // Yoki boshqa yo'naltirishni amalga oshirishingiz mumkin
        }
        model.addAttribute("schedule", nextSchedule);
        return "schedule_show";
    }


}
