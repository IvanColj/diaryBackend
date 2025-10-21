package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> findAllSchedule();

    List<ScheduleDTO> findScheduleWeekGroup(Long id);

    void save(ScheduleDTO scheduleDTO);
}
