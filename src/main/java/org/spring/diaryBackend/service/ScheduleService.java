package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.ScheduleDTO;
import org.spring.diaryBackend.dto.other.ScheduleWeekGroupDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> findAllSchedule();

    List<ScheduleWeekGroupDTO> findScheduleWeekGroup(Long id);

    List<ScheduleWeekGroupDTO> findBySchedule(Long idTeacher);

    void save(ScheduleDTO scheduleDTO);
}
