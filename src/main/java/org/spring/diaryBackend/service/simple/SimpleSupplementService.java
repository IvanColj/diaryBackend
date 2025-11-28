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

    private final PathService pathService;

    private final SupplementDTOMapper supplementDTOMapper;

    private final LessonRepository lessonRepository;

    private final ChangeRepository changeRepository;

    @Override
    public List<SupplementDTO> findAllSupplement() {
        return supplementRepository.findAll().stream().map(supplementDTOMapper).toList();
    }

    @Override
    public List<FilesDTO> findAllFilesSupplement(Long id) {
        return supplementRepository.findAllFilesSupplement(id);
    }

    @Override
    public void addFileSupplement(Long id, MultipartFile file) throws IOException {
        Path path = pathService.uploadFile(file, null, null);
        supplementRepository.addingFileSupplement(id, path.getId());
    }

    @Override
    public void deleteFileSupplement(Long idSupplement, Long idFile) {
        supplementRepository.deleteFileSupplement(idSupplement, idFile);
        pathService.deleteFile(idFile);
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
    @Transactional
    public void delete(Long id) {
        changeRepository.updateChange(id);
        lessonRepository.updateLesson(id);
        List<FilesDTO> filesDTOS = supplementRepository.findAllFilesSupplement(id);
        filesDTOS.forEach(filesDTO -> {
            supplementRepository.deleteFileSupplement(id, filesDTO.getId());
            pathService.deleteFile(filesDTO.getId());
        });
        supplementRepository.deleteById(id);
    }
}
