package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSocialCategoryDataDTO {
    private Long studentId;
    private Long categoryId;
    private Map<String, Object> data;
}
