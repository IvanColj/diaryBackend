package org.spring.diaryBackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.spring.diaryBackend.dbEnum.AttendanceStatus;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class AttendanceId implements Serializable {
    @Column(name = "id_student", nullable = false)
    private Long idStudent;

    @Column(name = "id_lesson", nullable = false)
    private Long idLesson;

    @Column(name = "status", nullable = false)
    private AttendanceStatus status;
}