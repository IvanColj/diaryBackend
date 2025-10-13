package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarksStudentDTO {
    Long number;
    Double value;
    Long idChange;
    Long idLesson;
    Long idTypeMark;
}
