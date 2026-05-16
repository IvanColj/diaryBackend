package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupStatsDTO {
    private Long groupNumber;
    private Long course;
    private String specialty;
    private Long studentsCount;
    private Long totalSocialCategories;
    private String leadersFio;
    private String curatorFio;
    private Double averageGrade;
    private Double attendancePercentage;
}
