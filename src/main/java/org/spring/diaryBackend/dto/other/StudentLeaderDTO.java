package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentLeaderDTO {
    private String fio;
    private String telephone;
    private String email;
}
