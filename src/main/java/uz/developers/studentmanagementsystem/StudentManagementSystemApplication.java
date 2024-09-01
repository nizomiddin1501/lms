package uz.developers.studentmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentManagementSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);

        // Avtomatik brauzerga o'tish
        //openBrowser();
    }

//    private static void openBrowser() {
//        try {
//            java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://localhost:8080/students"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }




}
