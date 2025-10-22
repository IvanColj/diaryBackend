package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "day_week")
    private String dayWeek;

    @Column(name = "type_week")
    private String typeWeek;

    @Column(name = "num_pair")
    private Long numPair;

    @Column(name = "room")
    private String room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_st")
    private SubjectTeacher idSt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group")
    private StudentGroup idGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subgroup")
    private Staff subgroup;

    @JoinColumn(name = "replacement")
    private Boolean replacement;

    @OneToMany(mappedBy = "idSchedule")
    private Set<Lesson> lessons = new LinkedHashSet<>();

}