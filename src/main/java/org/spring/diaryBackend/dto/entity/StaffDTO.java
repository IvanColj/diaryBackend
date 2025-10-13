package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    Long id;
    String patronymic;
    String name;
    String lastName;
    String login;
    String password;
    String email;
    List<StaffPositionDTO> StaffPosition;
}
