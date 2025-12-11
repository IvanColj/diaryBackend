package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "type_mark")
public class TypeMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_st")
    private SubjectTeacher idSt;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Long weight;

    @OneToMany(mappedBy = "idTypeMark")
    private Set<RegularMark> regularMarks = new LinkedHashSet<>();

}