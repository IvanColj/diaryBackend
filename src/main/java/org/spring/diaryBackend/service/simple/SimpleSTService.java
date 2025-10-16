package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SubjectTeacherDTO;
import org.spring.diaryBackend.dto.other.STGroupDTO;
import org.spring.diaryBackend.dto.other.STGroupsDTO;
import org.spring.diaryBackend.mapper.entity.STDTOMapper;
import org.spring.diaryBackend.model.SubjectTeacher;
import org.spring.diaryBackend.repository.STRepository;
import org.spring.diaryBackend.repository.StaffRepository;
import org.spring.diaryBackend.repository.StudentGroupRepository;
import org.spring.diaryBackend.repository.SubjectRepository;
import org.spring.diaryBackend.service.STService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleSTService implements STService {
    private final STRepository stRepository;
    private final StaffRepository staffRepository;
    private final SubjectRepository subjectRepository;
    private final StudentGroupRepository studentGroupRepository;

    private final STDTOMapper stdtoMapper;

    @Override
    public List<SubjectTeacherDTO> findAllSubjectTeacher() {
        return stRepository.findAll().stream().map(stdtoMapper).toList();
    }

    @Override
    public List<SubjectTeacherDTO> findByTeacher(Long teacherId) {
        return stRepository.findByTeacher(teacherId).stream().map(stdtoMapper).toList();
    }

    @Override
    public SubjectTeacherDTO findById(Long id) {
        return stRepository.findById(id).map(stdtoMapper).orElse(null);
    }

    @Override
    public List<STGroupsDTO> findBySTGroups(Long idTeacher) {
        List<STGroupDTO> stGroup = stRepository.findBySTGroup(idTeacher);

        Map<List<Object>, STGroupsDTO> map = new LinkedHashMap<>();

        for (STGroupDTO item : stGroup) {
            List<Object> key = Arrays.asList(item.getIdTeacher(), item.getIdSubject(), item.getSubjectName());
            STGroupsDTO dto = map.get(key);
            if (dto == null) {
                dto = new STGroupsDTO(item.getIdTeacher(), item.getIdSubject(), item.getSubjectName(), new ArrayList<>());
                map.put(key, dto);
            }
            dto.getIdGroups().add(item.getGroup());
        }

        return new ArrayList<>(map.values());
    }

    @Override
    public SubjectTeacherDTO saveSubjectTeacher(SubjectTeacherDTO subjectTeacherDTO) {
        SubjectTeacher subjectTeacher = new SubjectTeacher();
        subjectTeacher.setIdSubject(subjectRepository.findById(subjectTeacherDTO.getIdSubject()).orElse(null));
        subjectTeacher.setTeachers(subjectTeacherDTO.getTeachers().stream().map(
                idTeacher -> staffRepository.findById(idTeacher).orElse(null)
        ).collect(Collectors.toSet()));
        if (subjectTeacher.getIdSubject() == null || subjectTeacher.getTeachers() == null) {
            return new SubjectTeacherDTO();
        } else {
            return stdtoMapper.apply(stRepository.save(subjectTeacher));
        }
    }

    @Override
    public SubjectTeacherDTO updateSubjectTeacher(SubjectTeacherDTO subjectTeacherNew) {
        SubjectTeacher subjectTeacherUpdate = stRepository.findById(subjectTeacherNew.getId()).orElse(null);
        if (subjectTeacherUpdate == null) {
            return new SubjectTeacherDTO();
        }
        if (subjectTeacherNew.getIdSubject() != null) {
            subjectTeacherUpdate.setIdSubject(subjectRepository.findById(subjectTeacherNew.getIdSubject()).orElse(null));
        }
        if (subjectTeacherNew.getTeachers() != null) {
            subjectTeacherUpdate.setTeachers(subjectTeacherNew.getTeachers().stream().map(
                    idTeacher -> staffRepository.findById(idTeacher).orElse(null)
            ).collect(Collectors.toSet()));
        }
        if (subjectTeacherNew.getGroups() != null) {
            subjectTeacherUpdate.setGroups(
                    subjectTeacherNew.getGroups()
                            .stream()
                            .map(studentGroupRepository::findStudentGroupByIdGroup).collect(Collectors.toSet()));
        }
        if (subjectTeacherUpdate.getIdSubject() == null || subjectTeacherUpdate.getTeachers() == null) {
            return new SubjectTeacherDTO();
        }
        return stdtoMapper.apply(stRepository.save(subjectTeacherUpdate));
    }

    @Override
    public void addingSTGroup(Long id_st, Long group) {
        stRepository.addingSTGroup(id_st, group);
    }

    @Override
    public void deleteSTGroup(Long idSt, Long idGroup) {
        stRepository.deleteSTGroup(idSt, idGroup);
    }

    @Override
    public void deleteSubjectTeacher(Long idTeacher) {
        stRepository.deleteById(idTeacher);
    }
}
