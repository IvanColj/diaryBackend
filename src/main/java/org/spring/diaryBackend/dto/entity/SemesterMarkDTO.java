package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.model.SemesterMarkId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemesterMarkDTO {
    private SemesterMarkId id;
    private Double certification;
    private List<RegularMarkDTO> regularMarks;
}
