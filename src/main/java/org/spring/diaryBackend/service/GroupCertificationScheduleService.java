package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;
import org.spring.diaryBackend.dto.other.getAllCertificationGroupDTO;
import org.spring.diaryBackend.dto.other.getCurrentCertificationGroupDTO;

import java.util.List;

public interface GroupCertificationScheduleService {
    String getCurrentCertification(Long idSt, Long groupId);
    List<getCurrentCertificationGroupDTO> getCurrentCertificationGroup(Long idGroup);
    List<getAllCertificationGroupDTO> getAllCertificationGroup(Long idGroup);


    GroupCertificationScheduleDTO update(GroupCertificationScheduleDTO dto);
    void create(GroupCertificationScheduleDTO dto);
    void delete(Long idSt, Long idGroup, Long semester);
}
