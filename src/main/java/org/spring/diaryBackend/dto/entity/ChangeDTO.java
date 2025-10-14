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
    Long id;
    LocalDateTime dateTime;
    String action;
    Long idSupplement;
    Boolean teacherOrStudent;
    Double newValue;
}
