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
public class StudentInvalidDTO {
    private Long id;
    private String fio;
    private String specialty;
    private String certificate;
    private String status;
    private String limitationType;
    private LocalDate birthDate;
    private String address;
    private String educationForm;
}
