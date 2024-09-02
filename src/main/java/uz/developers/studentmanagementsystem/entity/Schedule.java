package uz.developers.studentmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {


    private Long id;
    private Long subject_id;
    private Long teacher_id;
    private Long classroom_id;
    private String dayOfWeek;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String subjectName;
    private String teacherName;
    private String classroomName;

    public Schedule(Long id, String subjectName, String teacherName, String classroomName,  String dayOfWeek, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.classroomName = classroomName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
