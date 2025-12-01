package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.ScheduleDTO;
import org.spring.diaryBackend.dto.other.GroupScheduleDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> findAllSchedule();

    List<GroupScheduleDTO> findScheduleWeekGroup(Long id);

    List<GroupScheduleDTO> findBySchedule(Long idTeacher);

    void save(ScheduleDTO scheduleDTO);
}
