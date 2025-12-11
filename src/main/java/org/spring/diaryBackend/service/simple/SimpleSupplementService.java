package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.dto.other.FilesDTO;
import org.spring.diaryBackend.mapper.entity.SupplementDTOMapper;
import org.spring.diaryBackend.model.Path;
import org.spring.diaryBackend.model.Supplement;
import org.spring.diaryBackend.repository.ChangeRepository;
import org.spring.diaryBackend.repository.LessonRepository;
import org.spring.diaryBackend.repository.SupplementRepository;
import org.spring.diaryBackend.service.PathService;
import org.spring.diaryBackend.service.SupplementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SimpleSupplementService implements SupplementService {
    private final SupplementRepository supplementRepository;
    private final LessonRepository lessonRepository;
    private final ChangeRepository changeRepository;
    private final PathService pathService;
    private final SupplementDTOMapper supplementDTOMapper;

    @Override
    public List<SupplementDTO> findAllSupplement() {
        return supplementRepository.findAll()
                .stream()
                .map(supplementDTOMapper)
                .toList();
    }

    @Override
    public List<FilesDTO> findAllFilesSupplement(Long id) {
        return supplementRepository.findAllSupplementFiles(id);
    }

    @Override
    public void addFileSupplement(Long id, MultipartFile file) throws IOException {
        Path path = pathService.uploadFile(file, null, null);
        supplementRepository.addSupplementFile(id, path.getId());
    }

    @Override
    public void update(Long id, String comment) {
        Supplement supplement = supplementRepository.findById(id).orElse(null);
        Objects.requireNonNull(supplement).setComment(comment);
        supplementRepository.save(supplement);
    }

    @Override
    public SupplementDTO save() {
        return supplementDTOMapper.apply(supplementRepository.save(new Supplement()));
    }

    @Override
    public void deleteFileSupplement(Long idSupplement, Long idFile) {
        supplementRepository.deleteSupplementFile(idSupplement, idFile);
        pathService.deleteFile(idFile);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        changeRepository.updateChange(id);
        lessonRepository.updateLessonSupplement(id);
        List<FilesDTO> filesDTOS = supplementRepository.findAllSupplementFiles(id);
        filesDTOS.forEach(filesDTO -> {
            supplementRepository.deleteSupplementFile(id, filesDTO.getId());
            pathService.deleteFile(filesDTO.getId());
        });
        supplementRepository.deleteById(id);
    }
}