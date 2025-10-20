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
public class ColumnMarkDTO {
    private LocalDate dateLesson;
    private String typeMark;
    private Long idSupplement;
    private String comment;
    private List<FilesDTO> files;
}
