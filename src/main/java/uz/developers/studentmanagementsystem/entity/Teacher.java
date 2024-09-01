package uz.developers.studentmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String password;
    private String photo;
    private Long subjectId;

    private String subjectName;




    public Teacher(Long id, String firstname, String lastname, String email, String gender, String password, String photo, String subjectName) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.photo = photo;
        this.subjectName = subjectName;
    }

    public Teacher(Long id, String firstname, String lastname, String email, String gender, String photo, String subjectName) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.photo = photo;
        this.subjectName = subjectName;

    }
}
