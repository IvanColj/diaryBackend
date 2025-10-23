package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.SubgroupDTO;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.Subgroup;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubgroupDTOMapper implements Function<Subgroup, SubgroupDTO> {
    @Override
    public SubgroupDTO apply(Subgroup subgroup) {
        return new SubgroupDTO(
                subgroup.getId(),
                subgroup.getIdSt() != null ? subgroup.getIdSt().getId() : null,
                subgroup.getIdTeacher() != null ? subgroup.getIdTeacher().getId() : null,
                subgroup.getStudents().stream().map(Student::getId).toList()
        );
    }
}
