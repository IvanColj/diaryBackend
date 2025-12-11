package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectTeacherDTO {
    private Long id;
    private List<Long> teachers;
    private Long idSubject;
    private List<Long> groups;
}
