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
public class StudentAllMarksDTO {
    private STTeachersDTO STTeachersDTO;
    private List<MarksStudentDTO> marksBySt;
    private Long certification;
}
