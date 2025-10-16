package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.model.RegularMarkId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegularMarkDTO {
    private RegularMarkId id;
    private Double value;
    private Long idLesson;
    private TypeMarkDTO typeMark;
    private List<ChangeDTO> changes;
}
