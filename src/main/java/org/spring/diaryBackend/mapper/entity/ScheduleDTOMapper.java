package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.ScheduleDTO;
import org.spring.diaryBackend.model.Schedule;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ScheduleDTOMapper implements Function<Schedule, ScheduleDTO> {
    @Override
    public ScheduleDTO apply(Schedule schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getDayWeek(),
                schedule.getTypeWeek(),
                schedule.getNumPair(),
                schedule.getRoom(),
                schedule.getIdSt() != null ? schedule.getIdSt().getId() : null,
                schedule.getIdGroup() != null ? schedule.getIdGroup().getId() : null,
                schedule.getSubgroup() != null ? schedule.getSubgroup().getId() : null,
                schedule.getReplacement()
        );
    }
}
