package uz.developers.studentmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private Long id;
    private String name;
    private String description;
    private int credits;
    private String photo;

    private Long faculty_id;

    private Long subject_id;
    private String facultyName;
    private String subjectName;


    public Course(Long id, String name, String description, int credits, String facultyName, String subjectName, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.facultyName = facultyName;
        this.subjectName = subjectName;
        this.photo = photo;
    }
}
