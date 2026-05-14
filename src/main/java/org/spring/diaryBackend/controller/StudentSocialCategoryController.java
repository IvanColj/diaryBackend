package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentSocialCategoryDTO;
import org.spring.diaryBackend.service.StudentSocialCategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student-social-categories")
@AllArgsConstructor
public class StudentSocialCategoryController {
    private final StudentSocialCategoryService service;

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
