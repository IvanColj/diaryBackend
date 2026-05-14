package org.spring.diaryBackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GroupCertificationScheduleId implements Serializable {
    @Column(name = "id_st", insertable = false, updatable = false)
    private Long idSt;

    @Column(name = "id_group", insertable = false, updatable = false)
    private Long idGroup;

    @Column(name = "semester", insertable = false, updatable = false)
    private Long semester;
}
