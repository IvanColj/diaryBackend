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
public class PathDTO {
    private Long id;
    private String nameFile;
    private String pathToFile;
    private Boolean accessStudent;
    private Boolean accessTeacher;
    private List<Long> staffs;
}
