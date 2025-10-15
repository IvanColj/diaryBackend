package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subject_teacher")
public class SubjectTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject")
    private Subject idSubject;

    @ManyToMany
    @JoinTable(name = "teachers_st",
            joinColumns = @JoinColumn(name = "id_st"),
            inverseJoinColumns = @JoinColumn(name = "id_teacher"))
    private Set<Staff> teachers = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "subject_teacher_groups",
            joinColumns = @JoinColumn(name = "id_st"),
            inverseJoinColumns = @JoinColumn(name = "id_group"))
    private Set<StudentGroup> groups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSt")
    private Set<SemesterMark> semesterMarks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSt")
    private Set<Schedule> schedules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSt")
    private Set<TypeMark> typeMarks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSt")
    private Set<Subgroup> subgroups  = new LinkedHashSet<>();

}