package org.spring.diaryBackend.dto;

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
    String nameSubject;
    String nameTeacher;
    String lastnameTeacher;
    String surnameTeacher;
}
