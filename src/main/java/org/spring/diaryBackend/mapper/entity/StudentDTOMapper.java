package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.model.Student;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StudentDTOMapper implements Function<Student, StudentDTO>{
    @Override
    public StudentDTO apply(Student student) {
        if (student == null) {
            return new StudentDTO();
        }
        return new StudentDTO(
                student.getId(),
                student.getLastName(),
                student.getName(),
                student.getPatronymic(),
                student.getIdGroup() != null ? student.getIdGroup().getId() : null,
                student.getLogin(),
                student.getPassword(),
                student.getTelephone(),
                student.getBirthDate(),
                student.getAddress(),
                student.getEmail()
        );
    }
}
