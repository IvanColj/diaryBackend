package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentSocialCategoryDTO;
import org.spring.diaryBackend.dto.other.SocialCategoryCountDTO;
import org.spring.diaryBackend.dto.other.StudentBriefDTO;
import org.spring.diaryBackend.service.StudentSocialCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-social-categories")
@AllArgsConstructor
public class StudentSocialCategoryController {
    private final StudentSocialCategoryService service;

    @GetMapping("students/{groupId}/{categoryId}")
    public List<StudentBriefDTO> getStudentsByCategory(
            @PathVariable Long groupId,
            @PathVariable Long categoryId) {
        return service.findStudentsByCategory(groupId, categoryId);
    }

    @GetMapping("group-stats/{groupId}")
    public List<SocialCategoryCountDTO> getCategoriesCountForGroup(@PathVariable Long groupId) {
        return service.getCategoriesCountForGroup(groupId);
    }

    @PostMapping("save")
    public void save(@RequestBody StudentSocialCategoryDTO dto) {
        service.save(dto);
    }

    @PatchMapping("update")
    public void update(@RequestBody StudentSocialCategoryDTO dto) {
        service.update(dto);
    }

    @DeleteMapping("delete/{idStudent}/{idCategory}")
    public void delete(@PathVariable Long idStudent, @PathVariable Long idCategory) {
        service.delete(idStudent, idCategory);
    }
}
