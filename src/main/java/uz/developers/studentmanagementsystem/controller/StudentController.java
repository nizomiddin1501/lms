package uz.developers.studentmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.dao.StudentDao;
import uz.developers.studentmanagementsystem.entity.Student;
import uz.developers.studentmanagementsystem.service.StudentService;

import java.sql.SQLException;
import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }



    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // login logikasi
        return "redirect:/"; // login muvaffaqiyatli bo'lsa, index sahifasiga o'tish
    }



    @GetMapping("/students")
    public String getStudents(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model){
        Student student = new Student();
        model.addAttribute("student",student);
        return "create_student";
    }

    @PostMapping("/addStudent")
    public String saveStudent(@ModelAttribute("student") Student student,
                              @RequestParam("facultyName") String facultyName){
        studentService.addStudent(student, facultyName);
        return "redirect:/students";
    }


    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model){
        model.addAttribute("student",studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String editStudent(@PathVariable Long id,
            @ModelAttribute("student") Student student,
            Model model) {
        // get student from database by id
        Student studentById = studentService.getStudentById(id);
        studentById.setId(id);
        studentById.setFirstname(student.getFirstname());
        studentById.setLastname(student.getLastname());
        studentById.setEmail(student.getEmail());
        studentById.setGender(student.getGender());
        studentById.setPassword(student.getPassword());
        studentById.setPhoto(student.getPhoto());

        //save update student object

        studentService.editStudent(studentById);
        return "redirect:/students";

    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable int id){
        studentService.deleteStudent(id);
        return "redirect:/students";

    }

    @GetMapping("/students/show/{id}")
    public String showStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id); // Talabani ID bo'yicha olish
        model.addAttribute("student", student); // Talaba ma'lumotlarini modelga qo'shish
        return "student_show"; // student_show.html sahifasini qaytarish

    }

    @GetMapping("/students/show/next/{currentId}")
    public String showNextStudent(@PathVariable Long currentId, Model model) {
        Student nextStudent = studentService.getNextStudent(currentId);
        if (nextStudent == null) {
            // Agar keyingi student mavjud bo'lmasa, qaytib keladi yoki oxirgi studentga o'tadi
            return "redirect:/students";  // Yoki boshqa yo'naltirishni amalga oshirishingiz mumkin
        }
        model.addAttribute("student", nextStudent);
        return "student_show";
    }



}
