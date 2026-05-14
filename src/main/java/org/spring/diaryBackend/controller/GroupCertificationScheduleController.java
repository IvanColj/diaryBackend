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

    @DeleteMapping("delete/st/{idSt}/gpoup/{idGroup}/semester/{semester}")
    public void delete(@PathVariable Long idSt, @PathVariable Long idGroup, @PathVariable Long semester) {
        service.delete(idSt, idGroup, semester);
    }

    @PostMapping("save")
    public void save(@RequestBody GroupCertificationScheduleDTO dto) {
        service.create(dto);
    }

    @PatchMapping("update")
    public GroupCertificationScheduleDTO update(@RequestBody GroupCertificationScheduleDTO dto) {
        return service.update(dto);
    }

    @GetMapping("current/{idSt}/{groupId}")
    public String getCurrent(@PathVariable Long idSt, @PathVariable Long groupId) {
        return service.getCurrentCertification(idSt, groupId);
    }
}
