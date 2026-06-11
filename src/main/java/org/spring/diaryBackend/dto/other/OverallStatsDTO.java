package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverallStatsDTO {
    private Double averageGrade;
    private Double attendancePercentage;
}