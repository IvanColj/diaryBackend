package org.spring.diaryBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMarkDTO {
    Long student;
    Long stId;
    Double mark;
    Integer number;
}
