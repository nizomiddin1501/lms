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
    private Long faculty_id;
    private String facultyName;


}
