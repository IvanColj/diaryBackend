package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

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
    @JoinColumn(name = "id_lesson")
    private Lesson idLesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_mark")
    private TypeMark idTypeMark;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "regular_mark_change",
            joinColumns = {
                    @JoinColumn(name = "semester_mark_id_st", referencedColumnName = "semester_mark_id_st", nullable = false),
                    @JoinColumn(name = "semester_mark_id_student", referencedColumnName = "semester_mark_id_student", nullable = false),
                    @JoinColumn(name = "number", referencedColumnName = "number", nullable = false)
            },
            inverseJoinColumns = @JoinColumn(name = "id_change")
    )
    private Set<Change> changes = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subgroup")
    private Subgroup idSubgroup;

}