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
    private final SupplementRepository supplementRepository;
    private final SubgroupRepository subgroupRepository;
    private final StudentRepository studentRepository;
    private final ChangeRepository changeRepository;
    private final SemesterMarkDTOMapper semesterMarkDTOMapper;
    private final SubjectMarksDTOMapper subjectMarksDTOMapper;
    private final ChangeInfoDTOMapper changeInfoDTOMapper;

    @Override
    public MarksColumnDataDTO findColumnMarkInfo(Long idStudent, Long idSt, Long number) {
        MarksColumnDataDTO marksColumnDataDTO = markRepository.findMarksColumnInfo(idStudent, idSt, number);
        if (marksColumnDataDTO != null) {
            List<FilesDTO> filesDTOS = supplementRepository.findAllSupplementFiles(marksColumnDataDTO.getIdSupplement());
            marksColumnDataDTO.setFiles(filesDTOS);
            return marksColumnDataDTO;
        }
        return null;
    }

    @Override
    public MarkInfoDTO findMarkInfo(Long idStudent, Long idSt, Long number) {
        MarkInfoDTO markInfoDTO = markRepository.findMarkInfo(idStudent, idSt, number);
        if (markInfoDTO != null) {
            List<MarkChangeDTO> markChangeDTOS = changeRepository
                    .findMarkChanges(idSt, idStudent, number)
                    .stream()
                    .map(changeInfoDTOMapper)
                    .toList();
            markInfoDTO.setChanges(markChangeDTOS);
            List<FilesDTO> filesDTOS = supplementRepository.findAllSupplementFiles(markInfoDTO.getIdSupplement());
            markInfoDTO.setFiles(filesDTOS);
            return markInfoDTO;
        }
        return null;
    }

    @Override
    public List<SemesterMarkDTO> findAllMarks() {
        return markRepository.findAll()
                .stream()
                .map(semesterMarkDTOMapper)
                .toList();
    }

    @Override
    public List<SubjectMarksInfoDTO> findByStudentAndSubject(Long id_student, Long id_st) {
        return markRepository.findStudentRegularsMarkBySubject(id_student, id_st)
                .stream()
                .map(subjectMarksDTOMapper)
                .toList();
    }

    @Override
    public SemesterMarkDTO updateMarks(SemesterMarkDTO updateSemesterMarks) {
        SemesterMark semesterMark = markRepository.findStudentSemesterMarkBySubject(
                updateSemesterMarks.getId().getIdStudent(),
                updateSemesterMarks.getId().getIdSt()
        );

        List<RegularMark> regularMarkList = semesterMark.getRegularMarks().stream().toList();
        int numberMark = Math.toIntExact(updateSemesterMarks.getRegularMarks().get(0).getId().getNumber());

        for (RegularMark regularMarks : regularMarkList) {
            if (regularMarks.getId().getNumber() != null &&
                    regularMarks.getId().getNumber() == numberMark) {
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
        SemesterMark semesterMark = markRepository.findStudentSemesterMarkBySubject(
                semesterMarkDTO.getId().getIdStudent(),
                semesterMarkDTO.getId().getIdSt()
        );
        semesterMark.setCertification(semesterMarkDTO.getCertification());
        markRepository.save(semesterMark);
    }

    @Override
    public void updateMarksNumber(UpdateMarkDTO updateMarkDTO) {
        SemesterMark semesterMarks = markRepository.findStudentSemesterMarkBySubject(
                updateMarkDTO.getIdStudent(),
                updateMarkDTO.getIdSt()
        );
        if (semesterMarks == null) {
            return;
        }

        if (updateMarkDTO.getIdTypeMark() != null) {
            markRepository.updateMarksColumn(
                    updateMarkDTO.getIdTeacher(),
                    updateMarkDTO.getIdGroup(),
                    updateMarkDTO.getIdSt(),
                    updateMarkDTO.getNumber(),
                    LocalDateTime.now(),
                    updateMarkDTO.getIdTypeMark()
            );
        } else {
            semesterMarks.getRegularMarks().stream().toList()
                    .forEach(regularMarks -> {
                        if (regularMarks.getId().getNumber() != null &&
                                regularMarks.getId().getNumber().equals(updateMarkDTO.getNumber())) {
                            regularMarks.setValue(updateMarkDTO.getMark());
                        }
                    });
        }
        markRepository.save(semesterMarks);
    }

    @Override
    public void deleteMarksGroupSt(DelMarksGroup delMarksGroup) {
        delMarksGroup.getStudents().forEach(student ->
                markRepository.deleteMarksByNumber(student, delMarksGroup.getIdSt(), delMarksGroup.getNumber())
        );
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
        if (subgroupRepository.findTeacherSubgroupBySubject(crudMarksDTO.getIdSt(), crudMarksDTO.getIdTeacher()) != null) {
            markRepository.deleteMarksColumnFromSubgroup(
                    crudMarksDTO.getIdSt(),
                    crudMarksDTO.getIdGroup(),
                    crudMarksDTO.getIdTeacher(),
                    crudMarksDTO.getNumber()
            );
        } else {
            markRepository.deleteMarksColumnFromGroup(
                    crudMarksDTO.getIdSt(),
                    crudMarksDTO.getIdGroup(),
                    crudMarksDTO.getNumber()
            );
        }
    }

    @Override
    public void addMarksForGroup(CRUDMarksDTO crudMarksDTO) {
        Long numberMark = markRepository.findLargestMarkNumberBySubject(crudMarksDTO.getIdGroup(), crudMarksDTO.getIdSt());
        if (numberMark == null) {
            numberMark = 0L;
        }

        if (subgroupRepository.findTeacherSubgroupBySubject(crudMarksDTO.getIdSt(), crudMarksDTO.getIdTeacher()) != null) {
            Change change = new Change();
            change.setDateTime(LocalDateTime.now());
            change.setAction("добавление оценки");
            change.setTeacherOrStudent(true);
            Long idChange = changeRepository.save(change).getId();

            Subgroup subgroup = subgroupRepository.findTeacherSubgroupBySubject(crudMarksDTO.getIdSt(), crudMarksDTO.getIdTeacher());
            List<Student> students = studentRepository.findStudentsByGroup(crudMarksDTO.getIdGroup());

            for (Student student : students) {
                if (subgroup.getStudents().contains(student)) {
                    markRepository.addMarksColumnToSubgroup(
                            crudMarksDTO.getIdSt(),
                            student.getId(),
                            numberMark + 1,
                            crudMarksDTO.getIdLesson(),
                            idChange
                    );
                }
            }
        } else {
            markRepository.addMarksColumnToGroup(
                    crudMarksDTO.getIdGroup(),
                    crudMarksDTO.getIdSt(),
                    numberMark + 1,
                    crudMarksDTO.getIdLesson(),
                    LocalDateTime.now()
            );
        }
    }
}