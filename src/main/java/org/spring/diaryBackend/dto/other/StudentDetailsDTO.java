package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDetailsDTO {
    private LocalDate birthDate;
    private String telephone;
    private String email;
    private String address;
    private String educationBasis;
    private List<SocialCategoryInfoDTO> socialCategories;
}
