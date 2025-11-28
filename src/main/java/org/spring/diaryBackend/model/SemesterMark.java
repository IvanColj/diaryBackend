package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "semester_mark")
public class SemesterMark {
    @EmbeddedId
    private SemesterMarkId id;

    @MapsId("idSt")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_st", nullable = false)
    private SubjectTeacher idSt;

    @MapsId("idStudent")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student idStudent;

    @Column(name = "certification")
    private Long certification;

    @OneToMany(mappedBy = "semesterMark", fetch = FetchType.LAZY)
    private Set<RegularMark> regularMarks = new LinkedHashSet<>();
}