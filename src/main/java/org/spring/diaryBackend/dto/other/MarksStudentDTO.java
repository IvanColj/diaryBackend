package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.dto.entity.ChangeDTO;
import org.spring.diaryBackend.dto.entity.TypeMarkDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarksStudentDTO {
    Long number;
    Double value;
    Long idLesson;
    Long idSubgroup;
    TypeMarkDTO typeMark;
    List<ChangeDTO> changes;
}
