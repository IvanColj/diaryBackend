package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SubjectDTO;
import org.spring.diaryBackend.mapper.entity.SubjectDTOMapper;
import org.spring.diaryBackend.model.Subject;
import org.spring.diaryBackend.repository.SubjectRepository;
import org.spring.diaryBackend.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleSubjectService implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectDTOMapper subjectDTOMapper;

    @Override
    public List<SubjectDTO> findAllSubject() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectDTOMapper)
                .toList();
    }

    @Override
    public SubjectDTO findById(Long id) {
        return subjectRepository.findById(id)
                .map(subjectDTOMapper)
                .orElse(null);
    }

    @Override
    public SubjectDTO saveSubject(SubjectDTO subjectNew) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectNew.getSubjectName());
        return subjectDTOMapper.apply(subjectRepository.save(subject));
    }

    @Override
    public SubjectDTO updateSubject(SubjectDTO subjectNew) {
        Subject subjectUpdate = subjectRepository.findById(subjectNew.getId()).orElse(null);
        if (subjectUpdate == null) {
            return new SubjectDTO();
        }
        if (subjectNew.getSubjectName() != null) {
            subjectUpdate.setSubjectName(subjectNew.getSubjectName());
        }
        return subjectDTOMapper.apply(subjectRepository.save(subjectUpdate));
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}