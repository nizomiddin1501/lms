package uz.developers.studentmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.entity.Subject;
import uz.developers.studentmanagementsystem.service.SubjectService;

import java.util.List;

@Controller
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @GetMapping("/subjects")
    public String getSubjects(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "5") int size,
                              Model model) {
        // Subjectsni pagination bilan olish
        List<Subject> subjects = subjectService.getAllSubjects(page, size);

        // Umumiy subjectlar sonini olish
        int totalSubjects = subjectService.getTotalSubjects();
        int totalPages = (int) Math.ceil((double) totalSubjects / size);

        // Modelga ma'lumot qo'shish
        model.addAttribute("subjects", subjects);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);

        return "subjects";
    }

    @GetMapping("/subjects/new")
    public String createStudentForm(Model model){
        Subject subject = new Subject();
        model.addAttribute("subject",subject);
        return "create_subject";
    }

    @PostMapping("/addSubject")
    public String saveSubject(@ModelAttribute("subject") Subject subject){
        subjectService.addSubject(subject);
        return "redirect:/subjects";
    }


    @GetMapping("/subjects/edit/{id}")
    public String editSubjectForm(@PathVariable Long id, Model model){
        model.addAttribute("subject",subjectService.getSubjectById(id));
        return "edit_subject";
    }

    @PostMapping("/subjects/{id}")
    public String editSubject(@PathVariable Long id,
                              @ModelAttribute("subject") Subject subject,
                              Model model) {
        // get subject from database by id
        Subject subjectById = subjectService.getSubjectById(id);
        subjectById.setId(id);
        subjectById.setName(subject.getName());
        subjectById.setPhoto(subject.getPhoto());
        subjectById.setProfession(subject.getProfession());

        //save update subject object

        subjectService.editSubject(subjectById);
        return "redirect:/subjects";

    }

    @GetMapping("/subjects/{id}")
    public String deleteSubject(@PathVariable int id){
        subjectService.deleteSubject(id);
        return "redirect:/subjects";

    }

    @GetMapping("/subjects/show/{id}")
    public String showSubject(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getSubjectById(id); // Talabani ID bo'yicha olish
        model.addAttribute("subject", subject); // Talaba ma'lumotlarini modelga qo'shish
        return "subject_show"; // student_show.html sahifasini qaytarish

    }

    @GetMapping("/subjects/show/next/{currentId}")
    public String showNextSubject(@PathVariable Long currentId, Model model) {
        Subject nextSubject = subjectService.getNextSubject(currentId);
        if (nextSubject == null) {
            // Agar keyingi subject mavjud bo'lmasa, qaytib keladi yoki oxirgi subjectga o'tadi
            return "redirect:/subjects";  // Yoki boshqa yo'naltirishni amalga oshirishingiz mumkin
        }
        model.addAttribute("subject", nextSubject);
        return "subject_show";
    }













}
