package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.ScheduleDTO;
import org.spring.diaryBackend.dto.other.ScheduleWeekGroupDTO;
import org.spring.diaryBackend.mapper.entity.ScheduleDTOMapper;
import org.spring.diaryBackend.model.Schedule;
import org.spring.diaryBackend.model.Staff;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.Subgroup;
import org.spring.diaryBackend.repository.*;
import org.spring.diaryBackend.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public List<ScheduleWeekGroupDTO> findScheduleWeekGroup(Long id) {
        List<ScheduleWeekGroupDTO> scheduleDTOS = scheduleRepository.findScheduleWeekGroup(id);
        Subgroup subgroup;
        Student student;
        Staff teacher;
        Long idTeacher;
        for (ScheduleWeekGroupDTO scheduleDTO : scheduleDTOS) {
            if (scheduleDTO.getSubgroup() != null) {
                scheduleDTO.setIdTeacher(scheduleDTO.getSubgroup());
                teacher = staffRepository.findById(scheduleDTO.getSubgroup()).orElse(null);
                scheduleDTO.setLastnameTeacher(Objects.requireNonNull(teacher).getLastName());
                scheduleDTO.setNameTeacher(Objects.requireNonNull(teacher).getName());
                scheduleDTO.setPatronymicTeacher(Objects.requireNonNull(teacher).getPatronymic());
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
            else {
                idTeacher = Objects.requireNonNull(sTRepository.findById(scheduleDTO.getIdSt()).orElse(null)).getTeachers().stream().toList().get(0).getId();
                teacher = staffRepository.findById(idTeacher).orElse(null);
                scheduleDTO.setIdTeacher(scheduleDTO.getSubgroup());
                scheduleDTO.setIdTeacher(idTeacher);
                scheduleDTO.setLastnameTeacher(Objects.requireNonNull(teacher).getLastName());
                scheduleDTO.setNameTeacher(Objects.requireNonNull(teacher).getName());
                scheduleDTO.setPatronymicTeacher(Objects.requireNonNull(teacher).getPatronymic());
            }
        }
        return scheduleDTOS;
    }

    @Override
    public List<ScheduleWeekGroupDTO> findBySchedule(Long teacherId) {
        List<ScheduleWeekGroupDTO> scheduleWeekGroupDTOs = new ArrayList<>();
        List<Object[]> schedules = scheduleRepository.findBySchedule(teacherId);
        schedules.forEach(schedule -> {
            if (schedule != null) {
                ScheduleWeekGroupDTO dto = new ScheduleWeekGroupDTO(
                        (Long) ((Object[]) schedule)[0],
                        (String) ((Object[]) schedule)[1],
                        (String) ((Object[]) schedule)[2],
                        (Long) ((Object[]) schedule)[3],
                        (String) ((Object[]) schedule)[4],
                        (Long) ((Object[]) schedule)[5],
                        (Long) ((Object[]) schedule)[6],
                        (String) ((Object[]) schedule)[7],
                        (Long) ((Object[]) schedule)[8],
                        (String) ((Object[]) schedule)[9],
                        (String) ((Object[]) schedule)[10],
                        (String) ((Object[]) schedule)[11],
                        (Long) ((Object[]) schedule)[12],
                        (Long) ((Object[]) schedule)[13],
                        (Long) ((Object[]) schedule)[14],
                        (Boolean) ((Object[]) schedule)[15]
                );
                scheduleWeekGroupDTOs.add(dto);
            }
        });

        Subgroup subgroup;
        Student student;
        Staff teacher;
        Long idTeacher;
        for (ScheduleWeekGroupDTO scheduleDTO : scheduleWeekGroupDTOs) {
            if (scheduleDTO.getSubgroup() != null) {
                scheduleDTO.setIdTeacher(scheduleDTO.getSubgroup());
                teacher = staffRepository.findById(scheduleDTO.getSubgroup()).orElse(null);
                scheduleDTO.setLastnameTeacher(Objects.requireNonNull(teacher).getLastName());
                scheduleDTO.setNameTeacher(Objects.requireNonNull(teacher).getName());
                scheduleDTO.setPatronymicTeacher(Objects.requireNonNull(teacher).getPatronymic());
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
            else {
                idTeacher = Objects.requireNonNull(sTRepository.findById(scheduleDTO.getIdSt()).orElse(null)).getTeachers().stream().toList().get(0).getId();
                teacher = staffRepository.findById(idTeacher).orElse(null);
                scheduleDTO.setIdTeacher(scheduleDTO.getSubgroup());
                scheduleDTO.setIdTeacher(idTeacher);
                scheduleDTO.setLastnameTeacher(Objects.requireNonNull(teacher).getLastName());
                scheduleDTO.setNameTeacher(Objects.requireNonNull(teacher).getName());
                scheduleDTO.setPatronymicTeacher(Objects.requireNonNull(teacher).getPatronymic());
            }
        }
        return scheduleWeekGroupDTOs;
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
