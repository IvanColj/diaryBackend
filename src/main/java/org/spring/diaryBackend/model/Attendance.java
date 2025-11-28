package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.spring.diaryBackend.converter.AttendanceStatusConverter;
import org.spring.diaryBackend.dbEnum.AttendanceStatus;

@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {
    @EmbeddedId
    private AttendanceId id;

    @MapsId("idStudent")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student idStudent;

    @MapsId("idLesson")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_lesson", nullable = false)
    private Lesson idLesson;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @Convert(converter = AttendanceStatusConverter.class)
    @Column(name = "status", nullable = false)
    private AttendanceStatus status;

}