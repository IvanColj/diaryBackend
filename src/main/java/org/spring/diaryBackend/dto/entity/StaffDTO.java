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
    private Long id;
    private String patronymic;
    private String name;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private String telephone;
    private String note;
    private List<StaffPositionDTO> StaffPosition;
}
