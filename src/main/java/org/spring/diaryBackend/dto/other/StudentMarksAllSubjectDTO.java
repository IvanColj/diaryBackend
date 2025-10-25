package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentMarksAllSubjectDTO {
    private STNameSubjectDTO stNameSubjectDTO;
    private List<MarksStudentDTO> marksBySt;
    private Long certification;
}
