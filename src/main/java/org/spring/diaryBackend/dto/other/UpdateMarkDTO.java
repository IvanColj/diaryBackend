package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMarkDTO {
    private Long idStudent;
    private Long idGroup;
    private Long idSt;
    private Double mark;
    private Long number;
    private Long idTypeMark;
}
