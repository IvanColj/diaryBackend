package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subgroup")
public class Subgroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_st")
    private SubjectTeacher idSt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_teacher")
    private Staff idTeacher;

    @ManyToMany
    @JoinTable(name = "subgroup_student",
            joinColumns = @JoinColumn(name = "id_subgroup"),
            inverseJoinColumns = @JoinColumn(name = "id_student"))
    private Set<Student> students = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSubgroup")
    private Set<Attendance> attendances = new LinkedHashSet<>();
}