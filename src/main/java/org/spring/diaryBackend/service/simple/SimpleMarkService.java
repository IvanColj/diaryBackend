package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SemesterMarkDTO;
import org.spring.diaryBackend.dto.other.ColumnMarkDTO;
import org.spring.diaryBackend.dto.other.FilesDTO;
import org.spring.diaryBackend.dto.other.SubjectMarksDTO;
import org.spring.diaryBackend.dto.other.UpdateMarkDTO;
import org.spring.diaryBackend.logic.BeanUtils;
import org.spring.diaryBackend.logic.DelMarksGroup;
import org.spring.diaryBackend.mapper.entity.SemesterMarkDTOMapper;
import org.spring.diaryBackend.mapper.other.SubjectMarksDTOMapper;
import org.spring.diaryBackend.model.RegularMark;
import org.spring.diaryBackend.model.SemesterMark;
import org.spring.diaryBackend.model.SemesterMarkId;
import org.spring.diaryBackend.repository.MarkRepository;
import org.spring.diaryBackend.repository.SupplementRepository;
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
    public void deleteMarksNumberGroupST(Long idGroup, Long idSt, Long number) {
        markRepository.deleteMarksNumberGroupST(idGroup, idSt, number);
    }

    @Override
    public void addMarksForGroup(Long idGroup, Long idSt) {
        Long numberMark = markRepository.findLargestNumberRegularMarks(idGroup, idSt);
        if (numberMark == null) {
            numberMark = 0L;
        }
        markRepository.addRegularMarksForGroup(idGroup, idSt, numberMark + 1, null, LocalDateTime.now());
        System.out.println(LocalDateTime.now());
    }
}
