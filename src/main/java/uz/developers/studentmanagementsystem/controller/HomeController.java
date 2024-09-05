package uz.developers.studentmanagementsystem.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class HomeController {



    @GetMapping("/")
    public String home() {
        return "index";  // index.html sahifasini ko'rsatadi
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // login.html sahifasini ko'rsatadi
    }

}
