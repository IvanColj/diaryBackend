package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCategoryGroupDTO {
    private String category;
    private List<String> students;
}