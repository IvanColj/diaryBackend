package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentSocialCategoryDTO;

public interface StudentSocialCategoryService {
    void save(StudentSocialCategoryDTO dto);

    void update(StudentSocialCategoryDTO dto);

    void delete(Long idStudent, Long idCategory);
}
