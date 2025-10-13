package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "student_group")
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number_group")
    private Long numberGroup;

    @Column(name = "admission_year")
    private Long admissionYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curator")
    private Staff idCurator;

    @Column(name = "course")
    private Long course;

    @Column(name = "form_education")
    private String formEducation;

    @Column(name = "profile")
    private String profile;

    @Column(name = "specialty")
    private String specialty;

    @OneToMany(mappedBy = "idGroup")
    private Set<Student> students = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "groups")
    private Set<SubjectTeacher> subjectTeachers = new LinkedHashSet<>();
}