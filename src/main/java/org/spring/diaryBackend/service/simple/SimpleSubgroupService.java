package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SubgroupDTO;
import org.spring.diaryBackend.dto.other.SubgroupStudentsDTO;
import org.spring.diaryBackend.mapper.entity.SubgroupDTOMapper;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.Subgroup;
import org.spring.diaryBackend.repository.STRepository;
import org.spring.diaryBackend.repository.StaffRepository;
import org.spring.diaryBackend.repository.StudentRepository;
import org.spring.diaryBackend.repository.SubgroupRepository;
import org.spring.diaryBackend.service.SubgroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleSubgroupService implements SubgroupService {
    private final SubgroupRepository subgroupRepository;

    private final SubgroupDTOMapper subgroupDTOMapper;

    private final STRepository sTRepository;

    private final StudentRepository studentRepository;

    private final StaffRepository staffRepository;

    @Override
    public List<SubgroupDTO> findAllSubgroup() {
        return subgroupRepository.findAll().stream().map(subgroupDTOMapper).toList();
    }

    @Override
    public void save(SubgroupDTO subgroupDTO) {
        Subgroup subgroup = new Subgroup();
        if (sTRepository.findById(subgroupDTO.getIdSt()).orElse(null) != null) {
            subgroup.setIdSt(sTRepository.findById(subgroupDTO.getIdSt()).orElse(null));
        }
        if (staffRepository.findById(subgroupDTO.getIdTeacher()).orElse(null) != null) {
            subgroup.setIdTeacher(staffRepository.findById(subgroupDTO.getIdTeacher()).orElse(null));
        }
        subgroup.setStudents(subgroupDTO.getStudents().stream().map(student -> studentRepository.findById(student).orElse(null)).collect(Collectors.toSet()));

        subgroupRepository.save(subgroup);
    }

    @Override
    public void addStudents(SubgroupStudentsDTO subgroupStudentsDTO) {
        Subgroup subgroup = subgroupRepository.findById(subgroupStudentsDTO.getId()).orElse(null);
        List<Long> students = new java.util.ArrayList<>(Objects.requireNonNull(subgroup).getStudents().stream().map(Student::getId).toList());
        students.addAll(subgroupStudentsDTO.getStudents());
        subgroup.setStudents(students.stream().map(student -> studentRepository.findById(student).orElse(null)).collect(Collectors.toSet()));
        subgroupRepository.save(subgroup);
    }

    @Override
    public void updateStudents(SubgroupStudentsDTO subgroupStudentsDTO, Long idSt, Long idTeacher) {
        Subgroup subgroupOne = subgroupRepository.findById(subgroupStudentsDTO.getId()).orElse(null);
        Subgroup subgroupTwo = subgroupRepository.findByIdStAndIdTeacher(idSt, idTeacher);
        List<Long> studentsSubgroupTwo = new java.util.ArrayList<>(Objects.requireNonNull(subgroupTwo).getStudents().stream().map(Student::getId).toList());
        studentsSubgroupTwo.addAll(subgroupStudentsDTO.getStudents());
        subgroupTwo.setStudents(studentsSubgroupTwo.stream().map(student -> studentRepository.findById(student).orElse(null)).collect(Collectors.toSet()));
        subgroupRepository.save(subgroupTwo);

        List<Long> studentsSubgroupOne = new java.util.ArrayList<>(Objects.requireNonNull(subgroupOne).getStudents().stream().map(Student::getId).toList());
        studentsSubgroupOne.removeAll(subgroupStudentsDTO.getStudents());
        subgroupOne.setStudents(studentsSubgroupOne.stream().map(student -> studentRepository.findById(student).orElse(null)).collect(Collectors.toSet()));
        subgroupRepository.save(subgroupOne);

    }

    @Override
    public void deleteStudents(SubgroupStudentsDTO subgroupStudentsDTO) {
        Subgroup subgroup = subgroupRepository.findById(subgroupStudentsDTO.getId()).orElse(null);
        List<Long> students = new java.util.ArrayList<>(Objects.requireNonNull(subgroup).getStudents().stream().map(Student::getId).toList());
        students.removeAll(subgroupStudentsDTO.getStudents());
        subgroup.setStudents(students.stream().map(student -> studentRepository.findById(student).orElse(null)).collect(Collectors.toSet()));
        subgroupRepository.save(subgroup);
    }
}
