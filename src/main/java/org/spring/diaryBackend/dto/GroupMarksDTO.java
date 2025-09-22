package org.spring.diaryBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.model.RegularMarks;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMarksDTO {
    private Long idStudent;
    private String lastName;
    private String name;
    private String surname;
    private List<RegularMarks> marks;
}
