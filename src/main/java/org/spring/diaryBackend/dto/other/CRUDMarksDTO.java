package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CRUDMarksDTO {
    private Long idGroup;
    private Long idSt;
    private Long idTeacher;
    private Long number;
}
