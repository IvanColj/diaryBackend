package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @Entity
@Table(name = "student_invalid")
public class StudentInvalid {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fio;
    private String specialty;
    private String certificate;
    private String status;
    private String limitationType;
    private LocalDate birthDate;
    private String address;
    private String educationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group")
    private StudentGroup studentGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student")
    private Student student;

    private String telephone;
}
