package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.ScheduleDTO;
import org.spring.diaryBackend.dto.other.GroupScheduleDTO;
import org.spring.diaryBackend.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/schedule")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping()
    public List<ScheduleDTO> findAllSchedule() {
        return scheduleService.findAllSchedule();
    }

    @GetMapping("group/{id}")
    public List<GroupScheduleDTO> findScheduleWeekGroup(@PathVariable Long id) {
        return scheduleService.findScheduleWeekGroup(id);
    }

    @GetMapping("teacher/{id}")
    public List<GroupScheduleDTO> getSchedule(@PathVariable Long id) {
        return scheduleService.findBySchedule(id);
    }

    @PostMapping("save")
    public void save(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.save(scheduleDTO);
    }

    @DeleteMapping("delete/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }

    @PatchMapping("update-ignored/{id}")
    public void updateIgnored(@PathVariable Long id, @RequestBody java.util.Map<String, Boolean> body) {
        Boolean isIgnored = body.get("isIgnored");
        scheduleService.updateIgnoredStatus(id, isIgnored);
    }
}