package org.spring.diaryBackend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMarksDTO {
    private String name;
    private String last_name;
    private Long id_student;
    private List<Double> marks;
}
