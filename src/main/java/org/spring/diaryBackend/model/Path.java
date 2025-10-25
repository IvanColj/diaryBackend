package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "path")
public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "path_to_file")
    private String pathToFile;

    @Column(name = "id_student")
    private Long idStudent;

    @Column(name = "access_teacher")
    private Boolean accessTeacher;

    @Column(name = "name_file")
    private String nameFile;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "paths")
    private Set<Supplement> supplements = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "paths")
    private Set<Staff> staffs = new LinkedHashSet<>();
}