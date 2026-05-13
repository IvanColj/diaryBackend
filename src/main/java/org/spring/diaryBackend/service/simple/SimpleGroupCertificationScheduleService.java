package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;
import org.spring.diaryBackend.mapper.entity.GroupCertificationScheduleDTOMapper;
import org.spring.diaryBackend.model.GroupCertificationSchedule;
import org.spring.diaryBackend.model.StudentGroup;
import org.spring.diaryBackend.model.SubjectTeacher;
import org.spring.diaryBackend.repository.GroupCertificationScheduleRepository;
import org.spring.diaryBackend.repository.STRepository;
import org.spring.diaryBackend.repository.StudentGroupRepository;
import org.spring.diaryBackend.service.GroupCertificationScheduleService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleGroupCertificationScheduleService implements GroupCertificationScheduleService {
    private final GroupCertificationScheduleRepository groupCertificationSchedulerepository;
    private final StudentGroupRepository studentGroupRepository;
    private final STRepository stRepository;
    private final GroupCertificationScheduleDTOMapper mapper;

    public GroupCertificationScheduleDTO create(GroupCertificationScheduleDTO dto) {
        GroupCertificationSchedule entity = new GroupCertificationSchedule();
        SubjectTeacher subjectTeacher = stRepository.findById(dto.getIdSt()).orElse(null);
        StudentGroup studentGroup = studentGroupRepository.findGroupById(dto.getIdGroup());
        entity.setSubjectTeacher(subjectTeacher);
        entity.setStudentGroup(studentGroup);
        entity.setSemester(dto.getSemester());
        entity.setCertificationType(dto.getCertificationType());
        return mapper.apply(groupCertificationSchedulerepository.save(entity));
    }

    public void delete(Long id) {
        groupCertificationSchedulerepository.deleteById(id);
    }

    public GroupCertificationScheduleDTO update(Long id, GroupCertificationScheduleDTO dto) {
        GroupCertificationSchedule entity = groupCertificationSchedulerepository.findById(id).orElseThrow();
        entity.setSemester(dto.getSemester());
        entity.setCertificationType(dto.getCertificationType());
        return mapper.apply(groupCertificationSchedulerepository.save(entity));
    }

    @Override
    public String getCurrentCertification(Long idSt, Long groupId) {
        StudentGroup group = studentGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Группа с id " + groupId + " не найдена"));

        Long currentSemester = group.getCurrentSemester();

        if (currentSemester == null) {
            return "Семестр не определен";
        }
        return groupCertificationSchedulerepository.findCertificationType(idSt, groupId, currentSemester)
                .orElse("Аттестация не назначена");
    }
}
