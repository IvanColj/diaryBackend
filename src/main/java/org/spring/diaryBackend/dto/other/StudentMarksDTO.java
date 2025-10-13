package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.dto.entity.RegularMarkDTO;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentMarksDTO {
    private String lastName;
    private String name;
    private String patronymic;
    private Map<Long, List<RegularMarkDTO>> marksBySt;
}
