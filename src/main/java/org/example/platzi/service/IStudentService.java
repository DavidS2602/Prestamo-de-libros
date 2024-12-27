package org.example.platzi.service;

import org.example.platzi.model.Student;


import java.util.List;
import java.util.Optional;

public interface IStudentService {
    void createStudent(Student student);
    List<Student> getAllStudents();
    Optional<Student> findStudentByDNI(String DNI);
}
