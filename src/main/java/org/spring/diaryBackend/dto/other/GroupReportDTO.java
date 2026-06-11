package org.spring.diaryBackend.dto.other;

import lombok.Data;

import java.util.List;

@Data
public class GroupReportDTO {
    private List<String> subjectNames;
    private List<Object> studentsData;
}