package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class STTeachersDTO {
    private Long idSt;
    private Long idSubject;
    private String nameSubject;
    private List<TeacherFIODTO> teachers;
}
