package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeachersDTO {
    private Long idTeacher;
    private String lastnameTeacher;
    private String nameTeacher;
    private String patronymicTeacher;
}
