package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroupDTO {
    private Long id;
    private Long numberGroup;
    private Long admissionYear;
    private Long idCurator;
    private Long course;
    private String formEducation;
    private String profile;
    private String specialty;
}
