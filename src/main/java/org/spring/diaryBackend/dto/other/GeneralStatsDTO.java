package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralStatsDTO {
    private long totalGroups;
    private long totalStudents;
}