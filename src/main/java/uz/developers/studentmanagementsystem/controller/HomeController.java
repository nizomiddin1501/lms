package uz.developers.studentmanagementsystem.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {



    @GetMapping({" ","/"})
    public String index() {
        return "index";  // index.html sahifasini ko'rsatadi
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";  // login.html sahifasini ko'rsatadi
//    }

}
