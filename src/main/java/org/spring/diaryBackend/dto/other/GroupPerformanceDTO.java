package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupPerformanceDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private Double averageGrade;
    private Long attendanceCount;
}
