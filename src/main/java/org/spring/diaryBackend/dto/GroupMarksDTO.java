package org.spring.diaryBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMarksDTO {
    private String name;
    private String last_name;
    private Long id_student;
    private Long numberGroup;
    private Map<Long, List<Double>> marksBySubject;
}
