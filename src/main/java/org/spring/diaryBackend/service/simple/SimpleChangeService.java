package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.ChangeDTO;
import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.dto.other.ChangeInfoDTO;
import org.spring.diaryBackend.mapper.entity.ChangeDTOMapper;
import org.spring.diaryBackend.mapper.other.ChangeInfoDTOMapper;
import org.spring.diaryBackend.model.Change;
import org.spring.diaryBackend.model.Supplement;
import org.spring.diaryBackend.repository.ChangeRepository;
import org.spring.diaryBackend.repository.SupplementRepository;
import org.spring.diaryBackend.service.ChangeService;
import org.spring.diaryBackend.service.SupplementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SimpleChangeService implements ChangeService {
    private final ChangeRepository changeRepository;

    private final SupplementService supplementService;

    private final SupplementRepository supplementRepository;

    private final ChangeInfoDTOMapper changeInfoDTOMapper;

    private final ChangeDTOMapper changeDTOMapper;

    @Override
    public List<ChangeDTO> findAllChange() {
        return changeRepository.findAll().stream().map(changeDTOMapper).toList();
    }

    @Override
    public List<ChangeInfoDTO> findByAllChangeMark(Long idSt, Long idStudent, Long numberMark) {
        return changeRepository.findByAllChangeMark(idSt, idStudent, numberMark).stream().map(changeInfoDTOMapper).toList();
    }

    @Override
    public ChangeDTO addSupplement(Long id) {
        Change change = changeRepository.findById(id).orElse(null);
        SupplementDTO supplementDTO = supplementService.save();
        Supplement supplement = supplementRepository.findById(supplementDTO.getId()).orElse(null);
        Objects.requireNonNull(change).setIdSupplement(supplement);
        changeRepository.save(change);
        return changeDTOMapper.apply(change);
    }

    @Override
    @Transactional
    public ChangeDTO saveStudent(Long idSt, Long idStudent, Long number) {
        Change change = new Change();
        change.setDateTime(LocalDateTime.now());
        change.setAction("комментарий студента");
        SupplementDTO supplementDTO = supplementService.save();
        Supplement supplement = supplementRepository.findById(supplementDTO.getId()).orElse(null);
        Objects.requireNonNull(change).setIdSupplement(supplement);
        change.setTeacherOrStudent(false);
        Change newChange = changeRepository.save(change);
        changeRepository.insertChange(idSt, idStudent, number, newChange.getId());
        return changeDTOMapper.apply(newChange);
    }

    @Override
    @Transactional
    public ChangeDTO saveTeacher(Long idSt, Long idStudent, Long number) {
        Change change = new Change();
        change.setDateTime(LocalDateTime.now());
        change.setAction("комментарий преподавателя");
        SupplementDTO supplementDTO = supplementService.save();
        Supplement supplement = supplementRepository.findById(supplementDTO.getId()).orElse(null);
        Objects.requireNonNull(change).setIdSupplement(supplement);
        change.setTeacherOrStudent(true);
        Change newChange = changeRepository.save(change);
        changeRepository.insertChange(idSt, idStudent, number, newChange.getId());
        return changeDTOMapper.apply(newChange);
    }
}
