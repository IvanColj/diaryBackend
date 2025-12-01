package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.other.STInfoDTO;
import org.spring.diaryBackend.dto.other.STTeachersDTO;
import org.spring.diaryBackend.dto.other.TeacherFIODTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class NameSubjectTeachersDTOMapper implements Function<STInfoDTO, STTeachersDTO> {
    @Override
    public STTeachersDTO apply(STInfoDTO stInfoDTO) {
        return new STTeachersDTO(
                stInfoDTO.getIdSt(),
                stInfoDTO.getIdSubject(),
                stInfoDTO.getNameSubject(),
                List.of(new TeacherFIODTO(
                        stInfoDTO.getIdTeacher(),
                        stInfoDTO.getLastnameTeacher(),
                        stInfoDTO.getNameTeacher(),
                        stInfoDTO.getPatronymicTeacher())
                )
        );
    }
}
