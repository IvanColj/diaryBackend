package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group")
    private StudentGroup idGroup;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "idStudent")
    private Set<Attendance> attendances = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idStudent")
    private Set<SemesterMark> semesterMarks = new LinkedHashSet<>();

}