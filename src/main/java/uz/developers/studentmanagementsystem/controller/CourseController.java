package uz.developers.studentmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.entity.Teacher;
import uz.developers.studentmanagementsystem.service.TeacherService;

@Controller
public class CourseController {

    private final TeacherService teacherService;

    @Autowired
    public CourseController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }




    @GetMapping("/teachers")
    public String getSubjects(Model model){
        model.addAttribute("teachers",teacherService.getAllTeachers());
        return "teachers";
    }

    @GetMapping("/teachers/new")
    public String createStudentForm(Model model){
        Teacher teacher = new Teacher();
        model.addAttribute("teacher",teacher);
        return "create_teacher";
    }

    @PostMapping("/addTeacher")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher,
                              @RequestParam("subjectName") String subjectName){
        teacherService.addTeacher(teacher, subjectName);
        return "redirect:/teachers";
    }


    @GetMapping("/teachers/edit/{id}")
    public String editSubjectForm(@PathVariable Long id, Model model){
        model.addAttribute("teacher",teacherService.getTeacherById(id));
        return "edit_teacher";
    }

    @PostMapping("/teachers/{id}")
    public String editTeacher(@PathVariable Long id,
                              @ModelAttribute("teacher") Teacher teacher,
                              Model model) {
        // get subject from database by id
        Teacher teacherById = teacherService.getTeacherById(id);
        teacherById.setId(id);
        teacherById.setFirstname(teacher.getFirstname());
        teacherById.setLastname(teacher.getLastname());
        teacherById.setEmail(teacher.getEmail());
        teacherById.setGender(teacher.getGender());
        teacherById.setPassword(teacher.getPassword());
        teacherById.setPhoto(teacher.getPhoto());

        //save update teacher object

        teacherService.editTeacher(teacherById);
        return "redirect:/teachers";

    }

    @GetMapping("/teachers/{id}")
    public String deleteTeacher(@PathVariable int id){
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";

    }

    @GetMapping("/teachers/show/{id}")
    public String showTeacher(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id); // Talabani ID bo'yicha olish
        model.addAttribute("teacher", teacher); // Talaba ma'lumotlarini modelga qo'shish
        return "teacher_show"; // teacher_show.html sahifasini qaytarish

    }

    @GetMapping("/teachers/show/next/{currentId}")
    public String showNextTeacher(@PathVariable Long currentId, Model model) {
        Teacher nextTeacher = teacherService.getNextTeacher(currentId);
        if (nextTeacher == null) {
            // Agar keyingi teacher mavjud bo'lmasa, qaytib keladi yoki oxirgi teacherga o'tadi
            return "redirect:/teachers ";  // Yoki boshqa yo'naltirishni amalga oshirishingiz mumkin
        }
        model.addAttribute("teacher", nextTeacher);
        return "teacher_show";
    }















}
