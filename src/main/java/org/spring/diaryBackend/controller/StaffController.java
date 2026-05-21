package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StaffDTO;
import org.spring.diaryBackend.dto.entity.SubjectDTO;
import org.spring.diaryBackend.dto.other.CourseSubjectsDTO;
import org.spring.diaryBackend.dto.other.GroupSubjectsDTO;
import org.spring.diaryBackend.service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/staffs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StaffController {
    private final StaffService staffService;

    @GetMapping
    public List<StaffDTO> findAllStaff() {
        return staffService.findAllStaff();
    }

    @GetMapping("id/{id}")
    public StaffDTO findById(@PathVariable Long id) {
        return staffService.findStaffById(id);
    }

    @GetMapping("login/{login}/password/{password}")
    public StaffDTO getByLogin(@PathVariable String login,
                               @PathVariable String password) {
        return staffService.findByLoginOrPassword(login, password);
    }

    @GetMapping("subjects/{id}")
    public List<SubjectDTO> findByAllSubject(@PathVariable Long id) {
        return staffService.findByAllSubject(id);
    }

    @GetMapping("subjects/course/{id}")
    public List<CourseSubjectsDTO> findBySubjectCourse(@PathVariable Long id) {
        return staffService.findBySubjectCourse(id);
    }

    @GetMapping("subjects/group/{id}")
    public List<GroupSubjectsDTO> findBySubjectGroup(@PathVariable Long id) {
        return staffService.findByGroup(id);
    }

    @GetMapping("note/{id}")
    public String getStaffNote(@PathVariable Long id) {
        return staffService.getStaffNote(id);
    }

    @PostMapping("addJob/id/{idStaff}/job/{idJob}")
    public void addStaffJob(@PathVariable Long idStaff,
                            @PathVariable Long idJob) {
        staffService.addStaffJob(idStaff, idJob);
    }

    @DeleteMapping("deleteJob/id/{idStaff}/job/{idJob}")
    public void deleteStaffJob(@PathVariable Long idStaff,
                               @PathVariable Long idJob) {
        staffService.deleteStaffJob(idStaff, idJob);
    }

    @PostMapping("save")
    public StaffDTO createStaff(@RequestBody StaffDTO staffDTO) {
        return staffService.saveStaff(staffDTO);
    }

    @PatchMapping("update")
    public StaffDTO updateStaff(@RequestBody StaffDTO staffDTO) {
        return staffService.updateStaff(staffDTO);
    }

    @DeleteMapping("delete/{id}")
    public void deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
    }
}