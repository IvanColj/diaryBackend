package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "change")
public class Change {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "action", length = Integer.MAX_VALUE)
    private String action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supplement")
    private Supplement idSupplement;

    @Column(name = "teacher_or_student")
    private Boolean teacherOrStudent;

    @Column(name = "new_value")
    private Double newValue;

    @ManyToMany(mappedBy = "changes")
    private Set<RegularMark> regularMarks = new LinkedHashSet<>();

}