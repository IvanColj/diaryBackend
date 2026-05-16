package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.GroupCertificationScheduleDTO;
import org.spring.diaryBackend.dto.other.getCurrentCertificationGroupDTO;
import org.spring.diaryBackend.service.GroupCertificationScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certification-schedule")
@AllArgsConstructor
public class GroupCertificationScheduleController {
    private final GroupCertificationScheduleService groupCertificationScheduleService;

    @GetMapping("current/{idSt}/{groupId}")
    public String getCurrent(@PathVariable Long idSt, @PathVariable Long groupId) {
        return groupCertificationScheduleService.getCurrentCertification(idSt, groupId);
    }

    @GetMapping("current-group/{groupId}")
    public List<getCurrentCertificationGroupDTO> getCurrentGroup(@PathVariable Long groupId) {
        return groupCertificationScheduleService.getCurrentCertificationGroup(groupId);
    }

    @PostMapping("save")
    public void save(@RequestBody GroupCertificationScheduleDTO dto) {
        groupCertificationScheduleService.create(dto);
    }

    @PatchMapping("update")
    public GroupCertificationScheduleDTO update(@RequestBody GroupCertificationScheduleDTO dto) {
        return groupCertificationScheduleService.update(dto);
    }

    @DeleteMapping("delete/st/{idSt}/gpoup/{idGroup}/semester/{semester}")
    public void delete(@PathVariable Long idSt, @PathVariable Long idGroup, @PathVariable Long semester) {
        groupCertificationScheduleService.delete(idSt, idGroup, semester);
    }
}
