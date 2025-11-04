package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SemesterMarkDTO;
import org.spring.diaryBackend.dto.other.*;
import org.spring.diaryBackend.logic.BeanUtils;
import org.spring.diaryBackend.logic.DelMarksGroup;
import org.spring.diaryBackend.mapper.entity.SemesterMarkDTOMapper;
import org.spring.diaryBackend.mapper.other.ChangeInfoDTOMapper;
import org.spring.diaryBackend.mapper.other.SubjectMarksDTOMapper;
import org.spring.diaryBackend.model.*;
import org.spring.diaryBackend.repository.*;
import org.spring.diaryBackend.service.MarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SimpleMarkService implements MarkService {
    private final MarkRepository markRepository;

    private final SemesterMarkDTOMapper semesterMarkDTOMapper;

    private final SubjectMarksDTOMapper subjectMarksDTOMapper;

    private final SupplementRepository supplementRepository;

    private final SubgroupRepository subgroupRepository;

    private final StudentRepository studentRepository;

    private final ChangeRepository changeRepository;

    private final ChangeInfoDTOMapper changeInfoDTOMapper;

    @Override
    public ColumnMarkDTO findColumnMarkInfo(Long idStudent, Long idSt, Long number) {
        ColumnMarkDTO columnMarkDTO = markRepository.findColumnMarkInfo(idStudent, idSt, number);
        if (columnMarkDTO != null) {
            List<FilesDTO> filesDTOS = supplementRepository.findAllFilesSupplement(columnMarkDTO.getIdSupplement());
            columnMarkDTO.setFiles(filesDTOS);
            return columnMarkDTO;
        }
        return null;
    }

    @Override
    public MarkInfoDTO findMarkInfo(Long idStudent, Long idSt, Long number) {
        MarkInfoDTO markInfoDTO = markRepository.findMarkInfo(idStudent, idSt, number);
        if (markInfoDTO != null) {
            List<ChangeInfoDTO> changeInfoDTOS = changeRepository.findByAllChangeMark(idSt, idStudent, number).stream().map(changeInfoDTOMapper).toList();
            markInfoDTO.setChanges(changeInfoDTOS);
            return markInfoDTO;
        }
        return null;
    }

    @Override
    public List<SemesterMarkDTO> findAllMarks() {
        return markRepository.findAll().stream().map(semesterMarkDTOMapper).toList();
    }

    @Override
    public List<SubjectMarksDTO> findByStudentAndSubject(Long id_student, Long id_st) {
        return markRepository.findByStudentSubject(id_student, id_st).stream().map(subjectMarksDTOMapper).toList();
    }

    @Override
    public SemesterMarkDTO updateMarks(SemesterMarkDTO updateSemesterMarks) {
        SemesterMark semesterMark = markRepository.findByStudentAndSubject(updateSemesterMarks.getId().getIdStudent(), updateSemesterMarks.getId().getIdSt());

        List<RegularMark> regularMarkList = semesterMark.getRegularMarks().stream().toList();
        int numberMark = Math.toIntExact(updateSemesterMarks.getRegularMarks().get(0).getId().getNumber());

        for (RegularMark regularMarks : regularMarkList) {
            if (regularMarks.getId().getNumber() != null && regularMarks.getId().getNumber() == numberMark) {
                BeanUtils.copyNonNullProperties(updateSemesterMarks.getRegularMarks().get(0), regularMarks);
            }
        }
        if (!Objects.equals(updateSemesterMarks.getCertification(), semesterMark.getCertification())) {
            semesterMark.setCertification(updateSemesterMarks.getCertification());
        }

        semesterMark.setRegularMarks(new HashSet<>(regularMarkList));

        return semesterMarkDTOMapper.apply(markRepository.save(semesterMark));
    }

    @Override
    public void updateCertification(SemesterMarkDTO semesterMarkDTO) {
        SemesterMark semesterMark = markRepository.findByStudentAndSubject(semesterMarkDTO.getId().getIdStudent(), semesterMarkDTO.getId().getIdSt());
        semesterMark.setCertification(semesterMarkDTO.getCertification());
        markRepository.save(semesterMark);
    }

    @Override
    public void updateMarksNumber(UpdateMarkDTO updateMarkDTO) {
        SemesterMark semesterMarks = markRepository.findByStudentAndSubject(updateMarkDTO.getIdStudent(), updateMarkDTO.getIdSt());
        if (semesterMarks == null) {
            return;
        }
        semesterMarks.getRegularMarks().stream().toList().forEach(regularMarks ->
        {
            if (regularMarks.getId().getNumber() != null && regularMarks.getId().getNumber().equals(updateMarkDTO.getNumber())) {
                regularMarks.setValue(updateMarkDTO.getMark());
            }
        });
        markRepository.save(semesterMarks);
    }

    @Override
    public void deleteMarksGroupSt(DelMarksGroup delMarksGroup) {
        delMarksGroup.getStudents().forEach(student -> markRepository.deleteMarksNumber(student, delMarksGroup.getIdSt(), delMarksGroup.getNumber()));
    }

    public void deleteMarks(Long idStudent, Long idSt) {
        SemesterMarkId id = new SemesterMarkId();
        id.setIdSt(idSt);
        id.setIdStudent(idStudent);
        markRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteMarksNumberGroupST(CRUDMarksDTO crudMarksDTO) {
        if (crudMarksDTO.getIdTeacher() != null) {
            markRepository.deleteMarksNumberSubGroupST(crudMarksDTO.getIdSt(), crudMarksDTO.getIdGroup(), crudMarksDTO.getIdTeacher(), crudMarksDTO.getNumber());
        }
        else {
            markRepository.deleteMarksNumberGroupST(crudMarksDTO.getIdSt(), crudMarksDTO.getIdGroup(), crudMarksDTO.getNumber());
        }
    }

    @Override
    public void addMarksForGroup(CRUDMarksDTO crudMarksDTO) {
        Long numberMark = markRepository.findLargestNumberRegularMarks(crudMarksDTO.getIdGroup(), crudMarksDTO.getIdSt());
        if (numberMark == null) {
            numberMark = 0L;
        }
        if (crudMarksDTO.getIdTeacher() != null) {
            Change change = new Change();
            change.setDateTime(LocalDateTime.now());
            change.setAction("добавление оценки");
            change.setTeacherOrStudent(true);
            Long idChange = changeRepository.save(change).getId();
            Subgroup subgroup = subgroupRepository.findByIdStAndIdTeacher(crudMarksDTO.getIdSt(), crudMarksDTO.getIdTeacher());
            List<Student> students = studentRepository.findByIdGroup(crudMarksDTO.getIdGroup());
            for (Student student : students) {
                if (subgroup.getStudents().contains(student)) {
                    markRepository.insertMarksNumber(crudMarksDTO.getIdSt(), student.getId(), numberMark + 1, crudMarksDTO.getIdLesson(), idChange);
                }
            }
        }
        else {
            markRepository.addRegularMarksForGroup(crudMarksDTO.getIdGroup(), crudMarksDTO.getIdSt(), numberMark + 1, crudMarksDTO.getIdLesson(), LocalDateTime.now());
        }
    }
}
