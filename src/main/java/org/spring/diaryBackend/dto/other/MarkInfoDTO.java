package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarkInfoDTO {
    private Double value;
    private Long number;
    private LocalDate dateLesson;
    private String typeMark;
    private String lastNameTeacher;
    private String nameTeacher;
    private String patronymicTeacher;
    private Long idSupplement;
    private String comment;
    private List<FilesDTO> files;
    private Long numberWeek;
    private String dayWeek;
    private String typeWeek;
    private Long numPair;
    private Boolean replacement;
    private List<ChangeInfoDTO> changes;
}

