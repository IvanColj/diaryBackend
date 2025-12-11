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
public class MarksColumnDataDTO {
    private LocalDate dateLesson;
    private String typeMark;
    private Long numberWeek;
    private String dayWeek;
    private String typeWeek;
    private Long numPair;
    private Boolean replacement;
    private Long idSupplement;
    private String comment;
    private List<FilesDTO> files;
}
