package org.spring.diaryBackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class RegularMarkId implements Serializable {

    @Column(name = "semester_mark_id_st", insertable = false, updatable = false)
    private Long semesterMarkIdSt;

    @Column(name = "semester_mark_id_student", insertable = false, updatable = false)
    private Long semesterMarkIdStudent;

    @Column(name = "number", nullable = false)
    private Long number;
}