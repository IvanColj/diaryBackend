package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;

public interface GroupCertificationScheduleService {
    void delete(Long id);

    GroupCertificationScheduleDTO create(GroupCertificationScheduleDTO dto);

    GroupCertificationScheduleDTO update(Long id, GroupCertificationScheduleDTO dto);

    String getCurrentCertification(Long idSt, Long groupId);
}
