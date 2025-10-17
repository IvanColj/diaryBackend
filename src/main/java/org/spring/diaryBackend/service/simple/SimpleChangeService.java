package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.ChangeDTO;
import org.spring.diaryBackend.mapper.entity.ChangeDTOMapper;
import org.spring.diaryBackend.repository.ChangeRepository;
import org.spring.diaryBackend.service.ChangeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleChangeService implements ChangeService {
    private final ChangeRepository changeRepository;

    private final ChangeDTOMapper changeDTOMapper;

    @Override
    public List<ChangeDTO> findAllChange() {
        return changeRepository.findAll().stream().map(changeDTOMapper).toList();
    }

    @Override
    public List<ChangeDTO> findByAllChangeMark(Long idSt, Long idStudent, Long numberMark) {
        return changeRepository.findByAllChangeMark(idSt, idStudent, numberMark).stream().map(changeDTOMapper).toList();
    }
}
