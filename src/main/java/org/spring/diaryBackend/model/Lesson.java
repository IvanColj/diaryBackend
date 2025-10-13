package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_schedule")
    private Schedule idSchedule;

    @Column(name = "number_week")
    private Long numberWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supplement")
    private Supplement idSupplement;

    @OneToMany(mappedBy = "idLesson")
    private Set<Attendance> attendances = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idLesson")
    private Set<RegularMark> regularMarks = new LinkedHashSet<>();

}