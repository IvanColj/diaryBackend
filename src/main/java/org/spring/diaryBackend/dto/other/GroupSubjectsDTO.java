package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupSubjectsDTO {
    private Long idSt;
    private Long numberGroup;
    private String specialty;
    private String subjectName;
    private Long countStudent;
}
