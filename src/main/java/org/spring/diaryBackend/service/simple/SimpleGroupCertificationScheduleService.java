package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;
import org.spring.diaryBackend.mapper.entity.GroupCertificationScheduleDTOMapper;
import org.spring.diaryBackend.model.GroupCertificationSchedule;
import org.spring.diaryBackend.model.GroupCertificationScheduleId;
import org.spring.diaryBackend.repository.GroupCertificationScheduleRepository;
import org.spring.diaryBackend.service.GroupCertificationScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SimpleGroupCertificationScheduleService implements GroupCertificationScheduleService {
    private final GroupCertificationScheduleRepository groupCertificationScheduleRepository;
    private final GroupCertificationScheduleDTOMapper mapper;

    public void create(GroupCertificationScheduleDTO dto) {
        groupCertificationScheduleRepository.saveCertification(dto.getId().getIdSt(), dto.getId().getIdGroup(), dto.getId().getSemester(), dto.getCertificationType());
    }

    public void delete(Long idSt, Long idGroup, Long semester) {
        GroupCertificationScheduleId key = new GroupCertificationScheduleId(idSt, idGroup, semester);
        groupCertificationScheduleRepository.deleteById(key);
    }

    @Override
    @Transactional
    public GroupCertificationScheduleDTO update(GroupCertificationScheduleDTO dto) {
        GroupCertificationScheduleId key = new GroupCertificationScheduleId(dto.getId().getIdSt(), dto.getId().getIdGroup(), dto.getId().getSemester());
        GroupCertificationSchedule entity = groupCertificationScheduleRepository.findById(key)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));

        entity.setCertificationType(dto.getCertificationType());
        return mapper.apply(groupCertificationScheduleRepository.save(entity));
    }

    @Override
    public String getCurrentCertification(Long idSt, Long groupId) {
        return groupCertificationScheduleRepository.findCertificationType(idSt, groupId)
                .orElse("Аттестация не назначена");
    }
}
