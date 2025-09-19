package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.SemesterMarks;
import org.spring.diaryBackend.model.SemesterMarksId;
import org.spring.diaryBackend.repository.MarksRepository;
import org.spring.diaryBackend.service.MarksService;
import org.springframework.stereotype.Service;

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
    public void saveMark(Long id_student, Long id_st, double mark) {
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
    public SemesterMarks updateMarksNumber(Long id_student, Long id_st, Double markValue, int number) {
        SemesterMarks semesterMarks = repository.findByStudentAndSubject(id_student, id_st);
        semesterMarks.getRegularMarks().get(number - 1).setValue(markValue);
        return repository.save(semesterMarks);
    }

    @Override
    public void deleteMarks(Long id_student, Long id_st) {
        SemesterMarksId id = new SemesterMarksId(id_student, id_st);
        repository.deleteById(id);
    }

    @Override
    public void deleteMarksNumber(Long id_student, Long id_st, Long offset) {
        repository.deleteMarksNumber(id_student, id_st, offset);
    }

    @Override
    public void addMarksForGroup(Long group, Long st_id) {
        repository.addMarksForGroup(group, st_id);
    }
}
