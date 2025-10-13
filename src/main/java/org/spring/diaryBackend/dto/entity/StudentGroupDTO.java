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
    Long id;
    Long numberGroup;
    Long admissionYear;
    Long idCurator;
    Long course;
    String formEducation;
    String profile;
    String specialty;
}
