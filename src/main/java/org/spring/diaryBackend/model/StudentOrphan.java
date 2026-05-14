package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @Entity
@Table(name = "student_orphan")
public class StudentOrphan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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