package uz.developers.studentmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.entity.Classroom;
import uz.developers.studentmanagementsystem.entity.Teacher;
import uz.developers.studentmanagementsystem.service.ClassroomService;
import uz.developers.studentmanagementsystem.service.TeacherService;

@Controller
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }




    @GetMapping("/classrooms")
    public String getSubjects(Model model){
        model.addAttribute("classrooms",classroomService.getAllTeachers());
        return "classrooms";
    }

    @GetMapping("/classrooms/new")
    public String createStudentForm(Model model){
        Classroom classroom = new Classroom();
        model.addAttribute("classroom",classroom);
        return "create_classroom";
    }

    @PostMapping("/addClassroom")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher,
                              @RequestParam("subjectName") String subjectName){
        classroomService.addTeacher(teacher, subjectName);
        return "redirect:/classrooms";
    }


    @GetMapping("/classrooms/edit/{id}")
    public String editSubjectForm(@PathVariable Long id, Model model){
        model.addAttribute("teacher",classroomService.getTeacherById(id));
        return "edit_classroom";
    }

    @PostMapping("/classrooms/{id}")
    public String editTeacher(@PathVariable Long id,
                              @ModelAttribute("teacher") Teacher teacher,
                              Model model) {
        // get subject from database by id
        Teacher teacherById = classroomService.getTeacherById(id);
        teacherById.setId(id);
        teacherById.setFirstname(teacher.getFirstname());
        teacherById.setLastname(teacher.getLastname());
        teacherById.setEmail(teacher.getEmail());
        teacherById.setGender(teacher.getGender());
        teacherById.setPassword(teacher.getPassword());
        teacherById.setPhoto(teacher.getPhoto());

        //save update teacher object

        classroomService.editTeacher(teacherById);
        return "redirect:/classrooms";

    }

    @GetMapping("/teachers/{id}")
    public String deleteTeacher(@PathVariable int id){
        classroomService.deleteTeacher(id);
        return "redirect:/classrooms";

    }

    @GetMapping("/classrooms/show/{id}")
    public String showTeacher(@PathVariable Long id, Model model) {
        Teacher teacher = classroomService.getTeacherById(id); // Talabani ID bo'yicha olish
        model.addAttribute("teacher", teacher); // Talaba ma'lumotlarini modelga qo'shish
        return "teacher_show"; // teacher_show.html sahifasini qaytarish

    }

    @GetMapping("/classrooms/show/next/{currentId}")
    public String showNextTeacher(@PathVariable Long currentId, Model model) {
        Teacher nextTeacher = classroomService.getNextTeacher(currentId);
        if (nextTeacher == null) {
            // Agar keyingi teacher mavjud bo'lmasa, qaytib keladi yoki oxirgi teacherga o'tadi
            return "redirect:/classrooms ";  // Yoki boshqa yo'naltirishni amalga oshirishingiz mumkin
        }
        model.addAttribute("teacher", nextTeacher);
        return "teacher_show";
    }















}
