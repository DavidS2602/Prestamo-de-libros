package org.example.platzi.service.impl;

import org.example.platzi.exceptions.DuplicateStudentException;
import org.example.platzi.exceptions.ErrorMessages;
import org.example.platzi.exceptions.StudentNotFoundException;
import org.example.platzi.model.Student;
import org.example.platzi.service.IStudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements IStudentService {
    private List<Student> students = new ArrayList<>();
    @Override
    public void createStudent(Student student ) {
        boolean isDuplicateStudent = students.stream()
                .anyMatch(s -> s.getName().equals(student.getName())
                        && s.getDNI().equals(student.getDNI()));

        if (isDuplicateStudent) {
            throw new DuplicateStudentException(
                    ErrorMessages.DUPLICATE_STUDENT.formatMessage(student.getDNI())
            );
        }
        students.add(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return students;
    }

    @Override
    public Optional<Student> findStudentByDNI(String DNI) {
        Optional<Student> optionalStudent = students.stream()
                .filter(book -> book.getDNI().equals(DNI))
                .findFirst();

        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(
                    ErrorMessages.STUDENT_NOT_FOUND.formatMessage(DNI)
            );
        }
        return optionalStudent;
    }
}
