package uz.developers.studentmanagementsystem.service;

import uz.developers.studentmanagementsystem.entity.Classroom;
import uz.developers.studentmanagementsystem.entity.Result;


import java.util.List;

public interface ClassroomService {


    List<Classroom> getAllClassrooms(int size, int page);

    int getTotalClassrooms();

    Classroom getClassroomById(Long id);

    Classroom getNextClassroom(Long currentClassroomId);

    Result addClassroom(Classroom classroom);

    boolean editClassroom(Classroom classroom);

    void deleteClassroom(int id);


}
