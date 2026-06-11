package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialCategoryInfoDTO {
    private String categoryName;
    private String categoryData;
}
