package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupAttendanceDTO {
    private Long idStudent;
    private String lastName;
    private String name;
    private String patronymic;
    private List<SingleSubjectAttendanceDTO> attendances;
}
