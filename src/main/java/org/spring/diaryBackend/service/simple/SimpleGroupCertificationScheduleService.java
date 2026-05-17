package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;
import org.spring.diaryBackend.dto.other.AllCertificationGroupDTO;
import org.spring.diaryBackend.dto.other.CurrentCertificationGroupDTO;
import org.spring.diaryBackend.mapper.entity.GroupCertificationScheduleDTOMapper;
import org.spring.diaryBackend.model.GroupCertificationSchedule;
import org.spring.diaryBackend.model.GroupCertificationScheduleId;
import org.spring.diaryBackend.repository.GroupCertificationScheduleRepository;
import org.spring.diaryBackend.service.GroupCertificationScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleGroupCertificationScheduleService implements GroupCertificationScheduleService {
    private final GroupCertificationScheduleRepository groupCertificationScheduleRepository;
    private final GroupCertificationScheduleDTOMapper mapper;

    @Override
    public List<CurrentCertificationGroupDTO> getCurrentCertificationGroup(Long idGroup) {
        return groupCertificationScheduleRepository.findCurrentCertificationGroup(idGroup);
    }

    @Override
    public List<AllCertificationGroupDTO> getAllCertificationGroup(Long idGroup) {
        // 1. Получаем все записи для группы из репозитория
        List<CurrentCertificationGroupDTO> allCerts = groupCertificationScheduleRepository.findCurrentCertificationGroup(idGroup);

        // 2. Группируем их по семестру: Map<Long, List<CertificationItemDTO>>
        Map<Long, List<CurrentCertificationGroupDTO>> groupedBySemester = allCerts.stream()
                .collect(Collectors.groupingBy(
                        CurrentCertificationGroupDTO::getSemester,
                        LinkedHashMap::new, // Используем LinkedHashMap, чтобы сохранить порядок семестров
                        Collectors.toList()
                ));

        // 3. Преобразуем Map в список AllCertificationGroupDTO
        return groupedBySemester.entrySet().stream()
                .map(entry -> new AllCertificationGroupDTO(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();
    }

    @Override
    public String getCurrentCertification(Long idSt, Long groupId) {
        return groupCertificationScheduleRepository.findCertificationType(idSt, groupId)
                .orElse("Аттестация не назначена");
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

    public void create(GroupCertificationScheduleDTO dto) {
        groupCertificationScheduleRepository.saveCertification(dto.getId().getIdSt(), dto.getId().getIdGroup(), dto.getId().getSemester(), dto.getCertificationType());
    }

    public void delete(Long idSt, Long idGroup, Long semester) {
        GroupCertificationScheduleId key = new GroupCertificationScheduleId(idSt, idGroup, semester);
        groupCertificationScheduleRepository.deleteById(key);
    }
}
