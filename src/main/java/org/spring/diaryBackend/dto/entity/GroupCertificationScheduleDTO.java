package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.model.GroupCertificationScheduleId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupCertificationScheduleDTO {
    private GroupCertificationScheduleId id;
    private String certificationType;
}
