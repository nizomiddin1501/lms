package uz.developers.studentmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {


    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String password;
    private String photo;

    private Long facultyId;
    private String facultyName;

    public Student(Long id, String firstname, String lastname, String email, String gender, String photo) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.photo = photo;
    }

    public Student(Long id, String firstname, String lastname, String email, String gender, String photo,Long facultyId, String facultyName) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.photo = photo;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
    }

    public Student(Long id, String firstname, String lastname, String email, String gender, String password, String photo, String facultyName) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.photo = photo;
        this.facultyName = facultyName;
    }

    public Student(long id, String firstname, String lastname, String gender, String photo, String facultyName) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.photo = photo;
        this.facultyName = facultyName;
    }
}
