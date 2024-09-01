package uz.developers.studentmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    private Long id;
    private Long studentId;
    private Long subjectId;
    private Date enrollmentDate;
    private String grade;
    private String studentName;
    private String subjectName;




}
