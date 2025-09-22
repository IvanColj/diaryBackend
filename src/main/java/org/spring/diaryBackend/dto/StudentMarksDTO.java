package org.spring.diaryBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.model.RegularMarks;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentMarksDTO {
    private String lastName;
    private String name;
    private String surname;
    private Map<Long, List<RegularMarks>> marksBySt;
}
