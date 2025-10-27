package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.ScheduleDTO;
import org.spring.diaryBackend.dto.other.ScheduleWeekGroupDTO;
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
    public List<ScheduleWeekGroupDTO> findScheduleWeekGroup(@PathVariable Long id) {
        return scheduleService.findScheduleWeekGroup(id);
    }

    @GetMapping("teacher/{id}")
    public List<ScheduleWeekGroupDTO> getSchedule(@PathVariable Long id) {
        return scheduleService.findBySchedule(id);
    }

    @PostMapping("save")
    public void save(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.save(scheduleDTO);
    }
}
