package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SubgroupDTO;
import org.spring.diaryBackend.dto.other.SubgroupStudentsDTO;
import org.spring.diaryBackend.service.SubgroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subgroups")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubgroupController {
    private final SubgroupService subgroupServices;

    @GetMapping()
    public List<SubgroupDTO> findAllSubgroup() {
        return subgroupServices.findAllSubgroup();
    }

    @PostMapping("save")
    public void save(@RequestBody SubgroupDTO subgroupDTO) {
        subgroupServices.save(subgroupDTO);
    }

    @PostMapping("add/students")
    public void save(@RequestBody SubgroupStudentsDTO subgroupStudentsDTO) {
        subgroupServices.addStudents(subgroupStudentsDTO);
    }

    @PostMapping("update/students/st/{idSt}/teacher/{idTeacher}")
    public void updateStudents(@RequestBody SubgroupStudentsDTO subgroupStudentsDTO, @PathVariable Long idSt, @PathVariable Long idTeacher) {
        subgroupServices.updateStudents(subgroupStudentsDTO, idSt, idTeacher);
    }

    @DeleteMapping("/delete/students")
    public void deleteStudents(@RequestBody SubgroupStudentsDTO subgroupStudentsDTO) {
        subgroupServices.deleteStudents(subgroupStudentsDTO);
    }
}
