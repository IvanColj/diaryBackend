package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentOrphanDTO;
import org.spring.diaryBackend.mapper.entity.StudentOrphanDTOMapper;
import org.spring.diaryBackend.model.StudentOrphan;
import org.spring.diaryBackend.repository.StudentOrphanRepository;
import org.spring.diaryBackend.service.StudentOrphanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleStudentOrphanService implements StudentOrphanService {
    private final StudentOrphanRepository repository;
    private final StudentOrphanDTOMapper mapper;

    @Override
    public List<StudentOrphanDTO> findAll() {
        return repository.findAll().stream().map(mapper).toList();
    }

    @Override
    @Transactional
    public StudentOrphanDTO save(StudentOrphanDTO dto) {
        StudentOrphan entity = new StudentOrphan();
        entity.setFio(dto.getFio());
        entity.setSpecialty(dto.getSpecialty());
        entity.setBirthDate(dto.getBirthDate());
        entity.setParentInfo(dto.getParentInfo());
        entity.setResidenceAddressPhone(dto.getResidenceAddressPhone());
        entity.setRegistrationAddress(dto.getRegistrationAddress());
        entity.setGuardian(dto.getGuardian());
        entity.setEducationForm(dto.getEducationForm());
        return mapper.apply(repository.save(entity));
    }

    @Override
    @Transactional
    public StudentOrphanDTO update(StudentOrphanDTO dto) {
        StudentOrphan entity = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Orphan record not found"));

        entity.setFio(dto.getFio());
        entity.setSpecialty(dto.getSpecialty());
        entity.setBirthDate(dto.getBirthDate());
        entity.setParentInfo(dto.getParentInfo());
        entity.setResidenceAddressPhone(dto.getResidenceAddressPhone());
        entity.setRegistrationAddress(dto.getRegistrationAddress());
        entity.setGuardian(dto.getGuardian());
        entity.setEducationForm(dto.getEducationForm());

        return mapper.apply(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}