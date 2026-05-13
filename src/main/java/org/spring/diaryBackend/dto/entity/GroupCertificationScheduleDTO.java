package org.spring.diaryBackend.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupCertificationScheduleDTO {
    private Long id;
    private Long idSt;
    private Long idGroup;
    private Long semester;
    private String certificationType;
}
