package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;
import org.spring.diaryBackend.dto.other.AllCertificationGroupDTO;
import org.spring.diaryBackend.dto.other.CurrentCertificationGroupDTO;

import java.util.List;

public interface GroupCertificationScheduleService {
    String getCurrentCertification(Long idSt, Long groupId);
    List<CurrentCertificationGroupDTO> getCurrentCertificationGroup(Long idGroup);
    List<AllCertificationGroupDTO> getAllCertificationGroup(Long idGroup);


    GroupCertificationScheduleDTO update(GroupCertificationScheduleDTO dto);
    void create(GroupCertificationScheduleDTO dto);
    void delete(Long idSt, Long idGroup, Long semester);
}
