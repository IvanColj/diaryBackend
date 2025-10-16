package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String action;
    private Long idSupplement;
    private Boolean teacherOrStudent;
    private Double oldValue;
}
