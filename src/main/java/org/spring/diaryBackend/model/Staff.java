package org.spring.diaryBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "staff_job_position",
            joinColumns = @JoinColumn(name = "id_staff"),
            inverseJoinColumns = @JoinColumn(name = "id_staff_position")
    )
    private Set<StaffPosition> staffPositions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTeacher")
    private Set<Subgroup> subgroups  = new LinkedHashSet<>();
}