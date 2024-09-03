package uz.developers.studentmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.entity.Classroom;
import uz.developers.studentmanagementsystem.service.ClassroomService;

@Controller
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }


    @GetMapping("/classrooms")
    public String getClassrooms(Model model) {
        model.addAttribute("classrooms", classroomService.getAllClassrooms());
        return "classrooms";
    }

    @GetMapping("/classrooms/new")
    public String createClassroomForm(Model model) {
        Classroom classroom = new Classroom();
        model.addAttribute("classroom", classroom);
        return "create_classroom";
    }

    @PostMapping("/addClassroom")
    public String saveClassroom(@ModelAttribute("classroom") Classroom classroom) {
        classroomService.addClassroom(classroom);
        return "redirect:/classrooms";
    }

    @GetMapping("/classrooms/edit/{id}")
    public String editClassroomForm(@PathVariable Long id, Model model) {
        model.addAttribute("classroom", classroomService.getClassroomById(id));
        return "edit_classroom";
    }

    @PostMapping("/classrooms/{id}")
    public String editClassroom(@PathVariable Long id,
                                @ModelAttribute("classroom") Classroom classroom,
                                Model model) {
        // get subject from database by id
        Classroom classroomById = classroomService.getClassroomById(id);
        classroomById.setId(id);
        classroomById.setName(classroom.getName());
        classroomById.setCapacity(classroom.getCapacity());
        classroomById.setPhoto(classroom.getPhoto());

        //save update classroom object

        classroomService.editClassroom(classroomById);
        return "redirect:/classrooms";

    }

    @GetMapping("/classrooms/{id}")
    public String deleteClassroom(@PathVariable int id) {
        classroomService.deleteClassroom(id);
        return "redirect:/classrooms";

    }

    @GetMapping("/classrooms/show/{id}")
    public String showClassroom(@PathVariable Long id, Model model) {
        Classroom classroom = classroomService.getClassroomById(id); // Classroom ni ID bo'yicha olish
        model.addAttribute("classroom", classroom); // Classroom ma'lumotlarini modelga qo'shish
        return "classroom_show"; // classroom_show.html sahifasini qaytarish

    }

    @GetMapping("/classrooms/show/next/{currentId}")
    public String showNextClassroom(@PathVariable Long currentId, Model model) {
        Classroom nextClassroom = classroomService.getNextClassroom(currentId);
        if (nextClassroom == null) {
            // Agar keyingi classroom mavjud bo'lmasa, qaytib keladi yoki oxirgi classroom ga o'tadi
            return "redirect:/classrooms ";  // Yoki boshqa yo'naltirishni amalga oshirishingiz mumkin
        }
        model.addAttribute("classroom", nextClassroom);
        return "classroom_show";
    }


}
