package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentInvalidDTO;
import org.spring.diaryBackend.mapper.entity.StudentInvalidDTOMapper;
import org.spring.diaryBackend.model.StudentInvalid;
import org.spring.diaryBackend.repository.StudentInvalidRepository;
import org.spring.diaryBackend.service.StudentInvalidService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleStudentInvalidService implements StudentInvalidService {
    private final StudentInvalidRepository repository;
    private final StudentInvalidDTOMapper mapper;

    @Override
    public List<StudentInvalidDTO> findAll() {
        return repository.findAll().stream().map(mapper).toList();
    }

    @Override
    @Transactional
    public StudentInvalidDTO save(StudentInvalidDTO dto) {
        StudentInvalid entity = new StudentInvalid();
        entity.setFio(dto.getFio());
        entity.setSpecialty(dto.getSpecialty());
        entity.setCertificate(dto.getCertificate());
        entity.setStatus(dto.getStatus());
        entity.setLimitationType(dto.getLimitationType());
        entity.setBirthDate(dto.getBirthDate());
        entity.setAddress(dto.getAddress());
        entity.setEducationForm(dto.getEducationForm());
        return mapper.apply(repository.save(entity));
    }

    @Override
    @Transactional
    public StudentInvalidDTO update(StudentInvalidDTO dto) {
        StudentInvalid entity = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Invalid record not found"));

        entity.setFio(dto.getFio());
        entity.setSpecialty(dto.getSpecialty());
        entity.setCertificate(dto.getCertificate());
        entity.setStatus(dto.getStatus());
        entity.setLimitationType(dto.getLimitationType());
        entity.setBirthDate(dto.getBirthDate());
        entity.setAddress(dto.getAddress());
        entity.setEducationForm(dto.getEducationForm());

        return mapper.apply(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}