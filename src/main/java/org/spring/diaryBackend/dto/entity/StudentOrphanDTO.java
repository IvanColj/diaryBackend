package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentOrphanDTO {
    private Long id;
    private String fio;
    private String specialty;
    private LocalDate birthDate;
    private String parentInfo;
    private String residenceAddressPhone;
    private String registrationAddress;
    private String guardian;
    private String educationForm;
}