package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class STNameSubjectDTO {
    Long idSt;
    Long idSubject;
    String nameSubject;
    Long idTeacher;
    String lastnameTeacher;
    String nameTeacher;
    String patronymicTeacher;
}
