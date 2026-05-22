package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SocialCategoryDTO;
import org.spring.diaryBackend.dto.other.SocialCategoryStatsDTO;
import org.spring.diaryBackend.dto.other.UpdateSocialCategoryDataDTO;
import org.spring.diaryBackend.service.SocialCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/social-categories")
@AllArgsConstructor
public class SocialCategoryController {
    private final SocialCategoryService socialCategoryService;

    @GetMapping
    public List<SocialCategoryDTO> getAll() {
        return socialCategoryService.findAll();
    }

    @GetMapping("stats")
    public List<SocialCategoryStatsDTO> getCategoryStats() {
        return socialCategoryService.getCategoryStats();
    }

    @PostMapping("save")
    public void save(@RequestBody SocialCategoryDTO dto) {
        socialCategoryService.save(dto);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        socialCategoryService.delete(id);
    }

    @PatchMapping("update-data")
    public void updateData(@RequestBody UpdateSocialCategoryDataDTO dto) {
        socialCategoryService.updateStudentCategoryData(dto);
    }
}
