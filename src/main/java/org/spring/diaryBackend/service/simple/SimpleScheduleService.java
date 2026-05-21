package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.ScheduleDTO;
import org.spring.diaryBackend.dto.other.GroupScheduleDTO;
import org.spring.diaryBackend.mapper.entity.ScheduleDTOMapper;
import org.spring.diaryBackend.model.Schedule;
import org.spring.diaryBackend.model.Staff;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.Subgroup;
import org.spring.diaryBackend.repository.*;
import org.spring.diaryBackend.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SimpleScheduleService implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final STRepository sTRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final StaffRepository staffRepository;
    private final SubgroupRepository subgroupRepository;
    private final StudentRepository studentRepository;
    private final ScheduleDTOMapper scheduleDTOMapper;

    @Override
    public List<ScheduleDTO> findAllSchedule() {
        return scheduleRepository.findAll()
                .stream()
                .map(scheduleDTOMapper)
                .toList();
    }

    @Override
    public List<GroupScheduleDTO> findScheduleWeekGroup(Long id) {
        List<GroupScheduleDTO> scheduleDTOS = scheduleRepository.findGroupSchedule(id);
        Subgroup subgroup;
        Student student;
        Staff teacher;
        Long idTeacher;

        for (GroupScheduleDTO scheduleDTO : scheduleDTOS) {
            if (scheduleDTO.getSubgroup() != null) {
                scheduleDTO.setIdTeacher(scheduleDTO.getSubgroup());
                teacher = staffRepository.findById(scheduleDTO.getSubgroup()).orElse(null);
                scheduleDTO.setLastnameTeacher(Objects.requireNonNull(teacher).getLastName());
                scheduleDTO.setNameTeacher(Objects.requireNonNull(teacher).getName());
                scheduleDTO.setPatronymicTeacher(Objects.requireNonNull(teacher).getPatronymic());
                subgroup = subgroupRepository.findTeacherSubgroupBySubject(scheduleDTO.getIdSt(), scheduleDTO.getSubgroup());
                if (subgroup.getStudents() != null) {
                    student = studentRepository.findStudentsByGroup(scheduleDTO.getIdGroup()).get(0);
                    if (subgroup.getStudents().contains(student)) {
                        scheduleDTO.setSubgroup(1L);
                    } else {
                        scheduleDTO.setSubgroup(2L);
                    }
                }
            } else {
                if (sTRepository.findById(scheduleDTO.getIdSt()).orElse(null).getTeachers().size() != 0) {
                    idTeacher = sTRepository.findById(scheduleDTO.getIdSt()).orElse(null)
                            .getTeachers().stream().toList().get(0).getId();
                    teacher = staffRepository.findById(idTeacher).orElse(null);
                    scheduleDTO.setIdTeacher(scheduleDTO.getSubgroup());
                    scheduleDTO.setIdTeacher(idTeacher);
                    scheduleDTO.setLastnameTeacher(Objects.requireNonNull(teacher).getLastName());
                    scheduleDTO.setNameTeacher(Objects.requireNonNull(teacher).getName());
                    scheduleDTO.setPatronymicTeacher(Objects.requireNonNull(teacher).getPatronymic());
                }
            }
        }
        return scheduleDTOS;
    }

    @Override
    public List<GroupScheduleDTO> findBySchedule(Long teacherId) {
        List<GroupScheduleDTO> groupScheduleDTOS = new ArrayList<>();
        List<Object[]> schedules = scheduleRepository.findTeacherSchedule(teacherId);

        schedules.forEach(schedule -> {
            if (schedule != null) {
                GroupScheduleDTO dto = new GroupScheduleDTO(
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
                        (Boolean) ((Object[]) schedule)[15],
                        schedule[16] instanceof java.sql.Date ?
                                ((java.sql.Date) schedule[16]).toLocalDate() :
                                (LocalDate) schedule[16]
                );
                groupScheduleDTOS.add(dto);
            }
        });

        Subgroup subgroup;
        Student student;
        Staff teacher;
        Long idTeacher;

        for (GroupScheduleDTO scheduleDTO : groupScheduleDTOS) {
            if (scheduleDTO.getSubgroup() != null) {
                scheduleDTO.setIdTeacher(scheduleDTO.getSubgroup());
                teacher = staffRepository.findById(scheduleDTO.getSubgroup()).orElse(null);
                scheduleDTO.setLastnameTeacher(Objects.requireNonNull(teacher).getLastName());
                scheduleDTO.setNameTeacher(Objects.requireNonNull(teacher).getName());
                scheduleDTO.setPatronymicTeacher(Objects.requireNonNull(teacher).getPatronymic());
                subgroup = subgroupRepository.findTeacherSubgroupBySubject(scheduleDTO.getIdSt(), scheduleDTO.getSubgroup());
                if (subgroup.getStudents() != null) {
                    student = studentRepository.findStudentsByGroup(scheduleDTO.getIdGroup()).get(0);
                    if (subgroup.getStudents().contains(student)) {
                        scheduleDTO.setSubgroup(1L);
                    } else {
                        scheduleDTO.setSubgroup(2L);
                    }
                }
            } else {
                idTeacher = Objects.requireNonNull(sTRepository.findById(scheduleDTO.getIdSt()).orElse(null))
                        .getTeachers().stream().toList().get(0).getId();
                teacher = staffRepository.findById(idTeacher).orElse(null);
                scheduleDTO.setIdTeacher(scheduleDTO.getSubgroup());
                scheduleDTO.setIdTeacher(idTeacher);
                scheduleDTO.setLastnameTeacher(Objects.requireNonNull(teacher).getLastName());
                scheduleDTO.setNameTeacher(Objects.requireNonNull(teacher).getName());
                scheduleDTO.setPatronymicTeacher(Objects.requireNonNull(teacher).getPatronymic());
            }
        }
        return groupScheduleDTOS;
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
        if (scheduleDTO.getSubgroup() != null &&
                staffRepository.findById(scheduleDTO.getSubgroup()).orElse(null) != null) {
            schedule.setSubgroup(staffRepository.findById(scheduleDTO.getSubgroup()).orElse(null));
        }
        schedule.setReplacement(scheduleDTO.getReplacement());
        schedule.setDateReplacement(scheduleDTO.getDateReplacement());
        schedule.setIsIgnored(scheduleDTO.getIsIgnored());

        scheduleRepository.save(schedule);
    }
}