package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.other.NameSubjectTeachersDTO;
import org.spring.diaryBackend.dto.other.STNameSubjectDTO;
import org.spring.diaryBackend.dto.other.TeachersDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class NameSubjectTeachersDTOMapper implements Function<STNameSubjectDTO, NameSubjectTeachersDTO> {
    @Override
    public NameSubjectTeachersDTO apply(STNameSubjectDTO stNameSubjectDTO) {
        return new NameSubjectTeachersDTO(
                stNameSubjectDTO.getIdSt(),
                stNameSubjectDTO.getIdSubject(),
                stNameSubjectDTO.getNameSubject(),
                List.of(new TeachersDTO(stNameSubjectDTO.getIdTeacher(), stNameSubjectDTO.getLastnameTeacher(), stNameSubjectDTO.getNameTeacher(), stNameSubjectDTO.getPatronymicTeacher()))
        );
    }
}
