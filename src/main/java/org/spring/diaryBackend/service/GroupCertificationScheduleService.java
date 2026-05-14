package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;

public interface GroupCertificationScheduleService {
    void delete(Long idSt, Long idGroup, Long semester);

    void create(GroupCertificationScheduleDTO dto);

    GroupCertificationScheduleDTO update(GroupCertificationScheduleDTO dto);

    String getCurrentCertification(Long idSt, Long groupId);
}
