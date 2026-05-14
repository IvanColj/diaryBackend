package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SocialCategoryDTO;
import org.spring.diaryBackend.mapper.entity.SocialCategoryDTOMapper;
import org.spring.diaryBackend.model.SocialCategory;
import org.spring.diaryBackend.repository.SocialCategoryRepository;
import org.spring.diaryBackend.service.SocialCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleSocialCategoryService implements SocialCategoryService {
    private final SocialCategoryRepository socialCategoryRepository;

    private final SocialCategoryDTOMapper socialCategoryDTOMapper;

    @Override
    public List<SocialCategoryDTO> findAll() {
        return socialCategoryRepository.findAll()
                .stream()
                .map(socialCategoryDTOMapper)
                .toList();
    }

    @Override
    public SocialCategory save(SocialCategoryDTO dto) {
        SocialCategory socialCategory = new SocialCategory();
        socialCategory.setName(dto.getName());
        return socialCategoryRepository.save(socialCategory);
    }

    @Override
    public void delete(Long id) {
        socialCategoryRepository.deleteById(id);
    }
}
