package uz.developers.studentmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.entity.Faculty;
import uz.developers.studentmanagementsystem.entity.Teacher;
import uz.developers.studentmanagementsystem.service.FacultyService;
import uz.developers.studentmanagementsystem.service.TeacherService;

@Controller
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping("/faculties")
    public String getFaculties(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "faculties";
    }

    @GetMapping("/faculties/new")
    public String createFacultyForm(Model model) {
        Faculty faculty = new Faculty();
        model.addAttribute("faculty", faculty);
        return "create_faculty";
    }

    @PostMapping("/addFaculty")
    public String saveFaculty(@ModelAttribute("faculty") Faculty faculty) {
        facultyService.addFaculty(faculty);
        return "redirect:/faculties";
    }


    @GetMapping("/faculties/edit/{id}")
    public String editFacultyForm(@PathVariable Long id, Model model) {
        model.addAttribute("faculty", facultyService.getFacultyById(id));
        return "edit_faculty";
    }

    @PostMapping("/faculties/{id}")
    public String editFaculty(@PathVariable Long id,
                              @ModelAttribute("faculty") Faculty faculty,
                              Model model) {
        // get faculty from database by id
        Faculty facultyById = facultyService.getFacultyById(id);
        facultyById.setId(id);
        facultyById.setName(faculty.getName());
        facultyById.setDean(faculty.getDean());
        facultyById.setPhone(faculty.getPhone());
        facultyById.setEmail(faculty.getEmail());
        facultyById.setPhoto(faculty.getPhoto());

        //save update faculty object

        facultyService.editFaculty(facultyById);
        return "redirect:/faculties";

    }

    @GetMapping("/faculties/{id}")
    public String deleteFaculty(@PathVariable int id) {
        facultyService.deleteFaculty(id);
        return "redirect:/faculties";

    }

    @GetMapping("/faculties/show/{id}")
    public String showFaculty(@PathVariable Long id, Model model) {
        Faculty faculty = facultyService.getFacultyById(id); // Faculty ni ID bo'yicha olish
        model.addAttribute("faculty", faculty); // Faculty ma'lumotlarini modelga qo'shish
        return "faculty_show"; // faculty_show.html sahifasini qaytarish

    }

    @GetMapping("/faculties/show/next/{currentId}")
    public String showNextFaculty(@PathVariable Long currentId, Model model) {
        Faculty nextFaculty = facultyService.getNextFaculty(currentId);
        if (nextFaculty == null) {
            // Agar keyingi faculty mavjud bo'lmasa, qaytib keladi yoki oxirgi faculty ga o'tadi
            return "redirect:/faculties ";
        }
        model.addAttribute("faculty", nextFaculty);
        return "faculty_show";
    }


}
