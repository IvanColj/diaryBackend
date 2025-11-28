package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.StudentGroupDTO;
import org.spring.diaryBackend.model.StudentGroup;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StudentGroupDTOMapper implements Function<StudentGroup, StudentGroupDTO> {
    @Override
    public StudentGroupDTO apply(StudentGroup studentGroup) {
        if (studentGroup == null) {
            return new StudentGroupDTO();
        }
        return new StudentGroupDTO(
                studentGroup.getId(),
                studentGroup.getNumberGroup(),
                studentGroup.getAdmissionYear(),
                studentGroup.getIdCurator() != null ? studentGroup.getIdCurator().getId() : null,
                studentGroup.getCourse(),
                studentGroup.getFormEducation(),
                studentGroup.getProfile(),
                studentGroup.getSpecialty()
        );
    }
}
