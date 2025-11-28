package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInfoDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String action;
    private Long idSupplement;
    private String comment;
    private List<FilesDTO> files;
    private Boolean teacherOrStudent;
    private Double newValue;
}
