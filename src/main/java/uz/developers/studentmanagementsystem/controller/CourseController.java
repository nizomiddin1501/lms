package uz.developers.studentmanagementsystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.developers.studentmanagementsystem.entity.Course;
import uz.developers.studentmanagementsystem.service.CourseService;


@Controller
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/courses")
    public String getCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }

    @GetMapping("/courses/new")
    public String createCourseForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "create_course";
    }

    @PostMapping("/addCourse")
    public String saveCourse(@ModelAttribute("course") Course course,
                             @RequestParam("facultyName") String facultyName,
                             @RequestParam("subjectName") String subjectName) {
        courseService.addCourse(course, facultyName, subjectName);
        return "redirect:/courses";
    }


    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        return "edit_course";
    }

    @PostMapping("/courses/{id}")
    public String editCourse(@PathVariable Long id,
                             @ModelAttribute("course") Course course,
                             Model model) {
        // get course from database by id
        Course courseById = courseService.getCourseById(id);
        courseById.setId(id);
        courseById.setName(course.getName());
        courseById.setDescription(course.getDescription());
        courseById.setCredits(course.getCredits());
        courseById.setPhoto(course.getPhoto());

        //save update course object

        courseService.editCourse(courseById);
        return "redirect:/courses";

    }

    @GetMapping("/courses/{id}")
    public String deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";

    }

    @GetMapping("/courses/show/{id}")
    public String showCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id); // Course ni ID bo'yicha olish
        model.addAttribute("course", course); // Course ma'lumotlarini modelga qo'shish
        return "course_show"; // course_show.html sahifasini qaytarish

    }

    @GetMapping("/courses/show/next/{currentId}")
    public String showNextCourse(@PathVariable Long currentId, Model model) {
        Course nextCourse = courseService.getNextCourse(currentId);
        if (nextCourse == null) {
            // Agar keyingi course mavjud bo'lmasa, qaytib keladi yoki oxirgi teacherga o'tadi
            return "redirect:/courses ";  // Yoki boshqa yo'naltirishni amalga oshirishingiz mumkin
        }
        model.addAttribute("course", nextCourse);
        return "course_show";
    }


}
