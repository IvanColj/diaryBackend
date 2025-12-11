package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.spring.diaryBackend.dbEnum.JobPosition;

@Getter
@Setter
@Entity
@Table(name = "staff_position")
public class StaffPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private JobPosition name;
}