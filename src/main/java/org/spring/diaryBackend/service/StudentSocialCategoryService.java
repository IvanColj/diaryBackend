package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentSocialCategoryDTO;
import org.spring.diaryBackend.dto.other.SocialCategoryCountDTO;
import org.spring.diaryBackend.dto.other.StudentBriefDTO;

import java.util.List;

public interface StudentSocialCategoryService {
    List<StudentBriefDTO> findStudentsByCategory(Long groupId, Long categoryId);
    List<SocialCategoryCountDTO> getCategoriesCountForGroup(Long groupId);
    void save(StudentSocialCategoryDTO dto);

    void update(StudentSocialCategoryDTO dto);

    void delete(Long idStudent, Long idCategory);
}
