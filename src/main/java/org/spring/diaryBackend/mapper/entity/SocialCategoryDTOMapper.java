package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.SocialCategoryDTO;
import org.spring.diaryBackend.model.SocialCategory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SocialCategoryDTOMapper implements Function<SocialCategory, SocialCategoryDTO> {
    @Override
    public SocialCategoryDTO apply(SocialCategory entity) {
        return new SocialCategoryDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
