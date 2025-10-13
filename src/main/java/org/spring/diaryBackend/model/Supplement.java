package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "supplement")
public class Supplement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "file_path",
            joinColumns = @JoinColumn(name = "id_supplement"),
            inverseJoinColumns = @JoinColumn(name = "id_path")
    )
    private Set<Path> paths = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSupplement")
    private Set<Change> changes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSupplement")
    private Set<Lesson> lessons = new LinkedHashSet<>();
}