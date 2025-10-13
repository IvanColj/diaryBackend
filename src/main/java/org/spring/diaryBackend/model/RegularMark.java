package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regular_mark")
public class RegularMark {
    @EmbeddedId
    private RegularMarkId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "semester_mark_id_st", referencedColumnName = "id_st", nullable = false),
            @JoinColumn(name = "semester_mark_id_student", referencedColumnName = "id_student", nullable = false)
    })
    private SemesterMark semesterMark;

    @Column(name = "value")
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_change")
    private Change idChange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lesson")
    private Lesson idLesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_mark")
    private TypeMark idTypeMark;
}