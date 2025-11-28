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
public class SubjectMarksDTO {
    private Long number;
    private Double value;
    private String comment;
    private String typeMark;
    private LocalDate dateLesson;
}
