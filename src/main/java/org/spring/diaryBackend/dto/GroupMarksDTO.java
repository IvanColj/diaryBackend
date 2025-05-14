package org.spring.diaryBackend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMarksDTO {
    private String name;
    private String lastName;
    private Long idStudent;
    private List<Double> marks;
}
