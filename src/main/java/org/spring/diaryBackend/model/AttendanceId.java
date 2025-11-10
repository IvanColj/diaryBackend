package org.spring.diaryBackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class AttendanceId implements Serializable {
    @Column(name = "id_student", nullable = false)
    private Long idStudent;

    @Column(name = "id_lesson", nullable = false)
    private Long idLesson;
}