package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.model.RegularMarkId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegularMarkDTO {
    RegularMarkId id;
    Double value;
    Long idChange;
    Long idLesson;
    Long idTypeMark;
}
