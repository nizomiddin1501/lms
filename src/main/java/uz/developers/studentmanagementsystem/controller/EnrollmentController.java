package uz.developers.studentmanagementsystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.service.EnrollmentService;

@Controller
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

//
//    @GetMapping("/enrollments")
//    public String getSubjects(Model model) {
//        model.addAttribute("teachers", enrollmentService.getAllTeachers());
//        return "enrollments";
//    }
//
//    @GetMapping("/enrollments/new")
//    public String createStudentForm(Model model) {
//        Teacher teacher = new Teacher();
//        model.addAttribute("teacher", teacher);
//        return "create_teacher";
//    }
//
//    @PostMapping("/addTeacher")
//    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher,
//                              @RequestParam("subjectName") String subjectName) {
//        enrollmentService.addTeacher(teacher, subjectName);
//        return "redirect:/enrollments";
//    }
//
//
//    @GetMapping("/enrollments/edit/{id}")
//    public String editSubjectForm(@PathVariable Long id, Model model) {
//        model.addAttribute("teacher", enrollmentService.getTeacherById(id));
//        return "edit_teacher";
//    }
//
//    @PostMapping("/enrollments/{id}")
//    public String editTeacher(@PathVariable Long id,
//                              @ModelAttribute("teacher") Teacher teacher,
//                              Model model) {
//        // get subject from database by id
//        Teacher teacherById = enrollmentService.getTeacherById(id);
//        teacherById.setId(id);
//        teacherById.setFirstname(teacher.getFirstname());
//        teacherById.setLastname(teacher.getLastname());
//        teacherById.setEmail(teacher.getEmail());
//        teacherById.setGender(teacher.getGender());
//        teacherById.setPassword(teacher.getPassword());
//        teacherById.setPhoto(teacher.getPhoto());
//
//        //save update teacher object
//
//        enrollmentService.editTeacher(teacherById);
//        return "redirect:/enrollments";
//
//    }
//
//    @GetMapping("/enrollments/{id}")
//    public String deleteTeacher(@PathVariable int id) {
//        enrollmentService.deleteTeacher(id);
//        return "redirect:/teachers";
//
//    }
//
//    @GetMapping("/enrollments/show/{id}")
//    public String showTeacher(@PathVariable Long id, Model model) {
//        Teacher teacher = enrollmentService.getTeacherById(id); // Talabani ID bo'yicha olish
//        model.addAttribute("teacher", teacher); // Talaba ma'lumotlarini modelga qo'shish
//        return "teacher_show"; // teacher_show.html sahifasini qaytarish
//
//    }
//
//    @GetMapping("/enrollments/show/next/{currentId}")
//    public String showNextTeacher(@PathVariable Long currentId, Model model) {
//        Teacher nextTeacher = enrollmentService.getNextTeacher(currentId);
//        if (nextTeacher == null) {
//            // Agar keyingi teacher mavjud bo'lmasa, qaytib keladi yoki oxirgi teacherga o'tadi
//            return "redirect:/enrollments ";  // Yoki boshqa yo'naltirishni amalga oshirishingiz mumkin
//        }
//        model.addAttribute("teacher", nextTeacher);
//        return "teacher_show";
//    }


}
