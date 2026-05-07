package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String lastName;
    private String name;
    private String patronymic;
    private String lastNameGenitive;
    private String nameGenitive;
    private String patronymicGenitive;
    private Long idGroup;
    private String login;
    private String password;
    private String telephone;
    private LocalDate birthDate;
    private String address;
    private String email;
    private Long code;
    private Boolean isLeader;
}