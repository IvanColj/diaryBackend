package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.AttendanceDTO;
import org.spring.diaryBackend.dto.other.GroupAttendanceDTO;
import org.spring.diaryBackend.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/attendances")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("lesson/{idLesson}/student/{idStudent}")
    public AttendanceDTO findByLessonStudent(@PathVariable("idLesson") Long idLesson, @PathVariable("idStudent") Long idStudent) {
        return attendanceService.findByStAndStudent(idLesson, idStudent);
    }

    @GetMapping("group/{idGroup}/st/{idSt}/teacher/{idTeacher}")
    public List<GroupAttendanceDTO> findByGroupAttendance(@PathVariable("idGroup") Long idGroup,@PathVariable("idSt") Long idSt, @PathVariable("idTeacher")  Long idTeacher) {
        return attendanceService.findByGroupAttendance(idGroup, idSt, idTeacher);
    }

    @PatchMapping("student/{idStudent}")
    public void update(@PathVariable("idStudent") Long idStudent , @RequestBody AttendanceDTO attendanceDTO) {
        attendanceService.update(idStudent, attendanceDTO);
    }
}
