package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentOrphanDTO;
import org.spring.diaryBackend.dto.other.ExportStudentOrphanDTO;
import org.spring.diaryBackend.mapper.entity.StudentOrphanDTOMapper;
import org.spring.diaryBackend.mapper.other.ExportStudentOrphanDTOMapper;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.StudentGroup;
import org.spring.diaryBackend.model.StudentOrphan;
import org.spring.diaryBackend.repository.StudentGroupRepository;
import org.spring.diaryBackend.repository.StudentOrphanRepository;
import org.spring.diaryBackend.repository.StudentRepository;
import org.spring.diaryBackend.service.StudentOrphanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleStudentOrphanService implements StudentOrphanService {
    private final StudentOrphanRepository repository;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentOrphanDTOMapper studentOrphanDTOMapper;
    private final ExportStudentOrphanDTOMapper exportStudentOrphanDTOMapper;

    private final StudentRepository studentRepository;

    @Override
    public List<ExportStudentOrphanDTO> findAll() {
        return repository.findAll().stream().map(exportStudentOrphanDTOMapper).toList();
    }

    @Override
    @Transactional
    public StudentOrphanDTO save(StudentOrphanDTO dto) {
        StudentOrphan entity = new StudentOrphan();
        entity.setFio(dto.getFio());
        entity.setSpecialty(dto.getSpecialty());
        entity.setBirthDate(dto.getBirthDate());
        entity.setParentInfo(dto.getParentInfo());
        entity.setTelephone(dto.getTelephone());
        entity.setRegistrationAddress(dto.getRegistrationAddress());
        entity.setGuardian(dto.getGuardian());
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
        return studentOrphanDTOMapper.apply(repository.save(entity));
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
        entity.setTelephone(dto.getTelephone());
        entity.setRegistrationAddress(dto.getRegistrationAddress());
        entity.setGuardian(dto.getGuardian());
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

        return studentOrphanDTOMapper.apply(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}