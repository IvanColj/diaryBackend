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
    Long id;
    String lastName;
    String name;
    String patronymic;
    Long idGroup;
    String login;
    String password;
    String telephone;
    LocalDate birthDate;
    String address;
    String email;
}