package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;
import org.spring.diaryBackend.service.GroupCertificationScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certification-schedule")
@AllArgsConstructor
public class GroupCertificationScheduleController {
    private final GroupCertificationScheduleService service;

    @PostMapping("save")
    public GroupCertificationScheduleDTO save(@RequestBody GroupCertificationScheduleDTO dto) {
        return service.create(dto);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("update")
    public GroupCertificationScheduleDTO update(@RequestBody GroupCertificationScheduleDTO dto) {
        return service.update(dto.getId(), dto);
    }

    @GetMapping("current/{idSt}/{groupId}")
    public String getCurrent(@PathVariable Long idSt, @PathVariable Long groupId) {
        return service.getCurrentCertification(idSt, groupId);
    }
}
