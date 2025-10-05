package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.UpdateMarkDTO;
import org.spring.diaryBackend.logic.DelMarksGroup;
import org.spring.diaryBackend.model.SemesterMarks;
import org.spring.diaryBackend.model.SemesterMarksId;
import org.spring.diaryBackend.repository.MarksRepository;
import org.spring.diaryBackend.service.MarksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleMarksService implements MarksService {
    private final MarksRepository repository;

    @Override
    public List<SemesterMarks> findByAllMarks(int offset, int limit) {
        return repository.findByAllMarks(offset, limit);
    }

    @Override
    public List<SemesterMarks> findAllMarks() {
        return repository.findAll();
    }

    @Override
    public void saveMark(Long id_student, Long id_st, Double mark) {
        repository.saveMark(id_student, id_st, mark);
    }

    @Override
    public SemesterMarks saveMarks(SemesterMarks semesterMarks) {
        return repository.save(semesterMarks);
    }

    @Override
    public List<SemesterMarks> findByStudentMarks(Long id) {
        return repository.findByStudentMarks(id);
    }

    @Override
    public List<SemesterMarks> findByObjectMarks(Long object) {
        return repository.findByObjectMarks(object);
    }

    @Override
    public SemesterMarks findByStudentAndSubject(Long id_student, Long id_st) {
        return repository.findByStudentAndSubject(id_student, id_st);
    }

    @Override
    public SemesterMarks updateMarks(SemesterMarks semesterMarks) {
        return repository.save(semesterMarks);
    }

    @Override
    public SemesterMarks updateMarksNumber(UpdateMarkDTO updateMarkDTO) {
        SemesterMarks semesterMarks = repository.findByStudentAndSubject(updateMarkDTO.getStudent(), updateMarkDTO.getStId());
        semesterMarks.getRegularMarks().get((int) (updateMarkDTO.getNumber() - 1L)).setValue(updateMarkDTO.getMark());
        return repository.save(semesterMarks);
    }

    @Override
    public void deleteMarksGroupSt(DelMarksGroup delMarksGroup) {
        delMarksGroup.getStudents().forEach(student -> deleteMarksNumber(student, delMarksGroup.getIdSt(), delMarksGroup.getNumber()));
    }

    @Override
    public void deleteMarks(Long id_student, Long id_st) {
        SemesterMarksId id = new SemesterMarksId(id_student, id_st);
        repository.deleteById(id);
    }

    @Override
    public void deleteMarksNumber(Long id_student, Long id_st, Long number) {
        repository.deleteMarksNumber(id_student, id_st, number);
    }

    @Override
    @Transactional
    public void deleteMarksNumberGroupST(Long number, Long group, Long st) {
        repository.deleteMarksNumberGroupST(number, group, st);
    }

    @Override
    public void addMarksForGroup(Long group, Long st_id) {
        Long numberMark = repository.findLargestNumberRegularMarks(group, st_id);
        repository.addRegularMarksForGroup(group, st_id, numberMark + 1, LocalDate.now());
    }
}
