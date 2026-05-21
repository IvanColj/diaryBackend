package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentInvalidDTO;
import org.spring.diaryBackend.dto.other.ExportStudentInvalidDTO;
import org.spring.diaryBackend.mapper.entity.StudentInvalidDTOMapper;
import org.spring.diaryBackend.mapper.other.ExportStudentInvalidDTOMapper;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.StudentGroup;
import org.spring.diaryBackend.model.StudentInvalid;
import org.spring.diaryBackend.repository.StudentGroupRepository;
import org.spring.diaryBackend.repository.StudentInvalidRepository;
import org.spring.diaryBackend.repository.StudentRepository;
import org.spring.diaryBackend.service.StudentInvalidService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleStudentInvalidService implements StudentInvalidService {
    private final StudentInvalidRepository repository;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentInvalidDTOMapper studentInvalidDTOMapper;
    private final ExportStudentInvalidDTOMapper exportStudentInvalidDTOMapper;

    private final StudentRepository studentRepository;

    @Override
    public List<ExportStudentInvalidDTO> findAll() {
        return repository.findAll().stream().map(exportStudentInvalidDTOMapper).toList();
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
        if (dto.getIdGroup() != null) {
            StudentGroup group = studentGroupRepository.findById(dto.getIdGroup())
                    .orElseThrow(() -> new RuntimeException("Группа не найдена"));
            entity.setStudentGroup(group);
        }
        if (dto.getIdStudent() != null) {
            Student student = studentRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Группа не найдена"));
            entity.setStudent(student);
        }
        entity.setTelephone(dto.getTelephone());
        return studentInvalidDTOMapper.apply(repository.save(entity));
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
        entity.setTelephone(dto.getTelephone());
        if (dto.getIdGroup() != null) {
            StudentGroup group = studentGroupRepository.findById(dto.getIdGroup())
                    .orElseThrow(() -> new RuntimeException("Группа не найдена"));
            entity.setStudentGroup(group);
        }
        if (dto.getIdStudent() != null) {
            Student student = studentRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Группа не найдена"));
            entity.setStudent(student);
        }

        return studentInvalidDTOMapper.apply(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}