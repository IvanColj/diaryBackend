package org.spring.diaryBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class Group {
    @Id
    private Long numberGroup;
    private Long idCurator;
    private Long course;
    private String specialty;
    private String profile;
    private String formEducation;
}
