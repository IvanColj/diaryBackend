package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupScheduleDTO {
    private Long id;
    private String dayWeek;
    private String typeWeek;
    private Long numPair;
    private String room;
    private Long idSt;
    private Long idSubject;
    private String nameSubject;
    private Long idTeacher;
    private String lastnameTeacher;
    private String nameTeacher;
    private String patronymicTeacher;
    private Long idGroup;
    private Long numberGroup;
    private Long subgroup;
    private Boolean replacement;
    private LocalDate dateReplacement;
}
