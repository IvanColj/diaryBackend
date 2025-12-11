package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SubgroupDTO;
import org.spring.diaryBackend.dto.other.SubgroupStudentsDTO;

import java.util.List;

public interface SubgroupService {
    List<SubgroupDTO> findAllSubgroup();

    void save(SubgroupDTO subgroupDTO);
    void addStudents(SubgroupStudentsDTO subgroupStudentsDTO);
    void updateStudents(SubgroupStudentsDTO subgroupStudentsDTO, Long idSt, Long idTeacher);
    void deleteStudents(SubgroupStudentsDTO subgroupStudentsDTO);
}
