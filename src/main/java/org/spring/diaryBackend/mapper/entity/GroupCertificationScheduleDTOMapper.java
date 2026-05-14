package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;
import org.spring.diaryBackend.model.GroupCertificationSchedule;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GroupCertificationScheduleDTOMapper implements Function<GroupCertificationSchedule, GroupCertificationScheduleDTO> {
    @Override
    public GroupCertificationScheduleDTO apply(GroupCertificationSchedule entity) {
        return new GroupCertificationScheduleDTO(
                entity.getId(),
                entity.getCertificationType()
        );
    }
}
