package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.ScheduleDTO;
import org.spring.diaryBackend.mapper.entity.ScheduleDTOMapper;
import org.spring.diaryBackend.model.Schedule;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.Subgroup;
import org.spring.diaryBackend.repository.*;
import org.spring.diaryBackend.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleScheduleService implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private final ScheduleDTOMapper scheduleDTOMapper;

    private final STRepository sTRepository;

    private final StudentGroupRepository studentGroupRepository;

    private final StaffRepository staffRepository;

    private final SubgroupRepository subgroupRepository;

    private final StudentRepository studentRepository;

    @Override
    public List<ScheduleDTO> findAllSchedule() {
        return scheduleRepository.findAll().stream().map(scheduleDTOMapper).toList();
    }

    @Override
    public List<ScheduleDTO> findScheduleWeekGroup(Long id) {
        List<ScheduleDTO> scheduleDTOS = scheduleRepository.findScheduleWeekGroup(id);
        Subgroup subgroup;
        Student student;
        for (ScheduleDTO scheduleDTO : scheduleDTOS) {
            if (scheduleDTO.getSubgroup() != null) {
                subgroup = subgroupRepository.findByIdStAndIdTeacher(scheduleDTO.getIdSt(), scheduleDTO.getSubgroup());
                if (subgroup.getStudents() != null) {
                    student = studentRepository.findByIdGroup(scheduleDTO.getIdGroup()).get(0);
                    if (subgroup.getStudents().contains(student)) {
                        scheduleDTO.setSubgroup(1L);
                    }
                    else {
                        scheduleDTO.setSubgroup(2L);
                    }
                }

            }
        }
        return scheduleDTOS;
    }

    @Override
    public void save(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDayWeek(scheduleDTO.getDayWeek());
        schedule.setTypeWeek(scheduleDTO.getTypeWeek());
        schedule.setNumPair(scheduleDTO.getNumPair());
        schedule.setRoom(scheduleDTO.getRoom());

        if (sTRepository.findById(scheduleDTO.getIdSt()).orElse(null) != null) {
            schedule.setIdSt(sTRepository.findById(scheduleDTO.getIdSt()).orElse(null));
        }
        if (studentGroupRepository.findById(scheduleDTO.getIdGroup()).orElse(null) != null) {
            schedule.setIdGroup(studentGroupRepository.findById(scheduleDTO.getIdGroup()).orElse(null));
        }
        if (scheduleDTO.getSubgroup() != null && staffRepository.findById(scheduleDTO.getSubgroup()).orElse(null) != null) {
            schedule.setSubgroup(staffRepository.findById(scheduleDTO.getSubgroup()).orElse(null));
        }
        schedule.setReplacement(scheduleDTO.getReplacement());

        scheduleRepository.save(schedule);
    }
}
