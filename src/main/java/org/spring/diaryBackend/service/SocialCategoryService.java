package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SocialCategoryDTO;
import org.spring.diaryBackend.model.SocialCategory;

import java.util.List;

public interface SocialCategoryService {
    List<SocialCategoryDTO> findAll();

    SocialCategory save(SocialCategoryDTO dto);

    void delete(Long id);
}
