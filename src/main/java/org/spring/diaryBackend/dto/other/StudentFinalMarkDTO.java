package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentFinalMarkDTO {
    private Long idStudent;
    private Long certification;
    private Boolean isRetake;
    private Long initialCertification;
}