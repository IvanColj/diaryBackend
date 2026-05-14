package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "group_certification_schedule")
public class GroupCertificationSchedule {
    @EmbeddedId
    private GroupCertificationScheduleId id;

    @ManyToOne @JoinColumn(name = "id_st")
    private SubjectTeacher subjectTeacher;

    @ManyToOne @JoinColumn(name = "id_group")
    private StudentGroup studentGroup;

    private Long semester;
    private String certificationType;
}
