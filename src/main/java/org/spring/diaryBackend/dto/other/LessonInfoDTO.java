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
public class LessonInfoDTO {
    private Long id;
    private Long numberWeek;
    private LocalDate date;
    private String dayWeek;
    private String typeWeek;
    private Long numPair;
    private Boolean replacement;
}
