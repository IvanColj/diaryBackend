package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.dto.other.FilesDTO;
import org.spring.diaryBackend.mapper.entity.SupplementDTOMapper;
import org.spring.diaryBackend.repository.SupplementRepository;
import org.spring.diaryBackend.service.SupplementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleSupplementService implements SupplementService {
    private final SupplementRepository supplementRepository;

    private final SupplementDTOMapper supplementDTOMapper;

    @Override
    public List<SupplementDTO> findAllSupplement() {
        return supplementRepository.findAll().stream().map(supplementDTOMapper).toList();
    }

    @Override
    public List<FilesDTO> findAllFilesSupplement(Long id) {
        return supplementRepository.findAllFilesSupplement(id);
    }
}
