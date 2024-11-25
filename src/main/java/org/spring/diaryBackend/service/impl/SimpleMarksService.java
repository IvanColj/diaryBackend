package org.spring.diaryBackend.service.impl;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.Marks;
import org.spring.diaryBackend.model.MarksId;
import org.spring.diaryBackend.repository.MarksRepository;
import org.spring.diaryBackend.service.MarksService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleMarksService implements MarksService {
    private final MarksRepository repository;

    @Override
    public List<Marks> findByAllMarks(int offset, int limit) {
        return repository.findByAllMarks(offset, limit);
    }

    @Override
    public List<Marks> findAllMarks() {
        return repository.findAll();
    }

    @Override
    public void saveMark(Long id_student, Long id_st, double mark) {
        repository.saveMark(id_student, id_st, mark);
    }

    @Override
    public Marks saveMarks(Marks marks) {
        return repository.save(marks);
    }

    @Override
    public List<Marks> findByStudentMarks(Long id) {
        return repository.findByStudentMarks(id);
    }

    @Override
    public List<Marks> findByObjectMarks(Long object) {
        return repository.findByObjectMarks(object);
    }

    @Override
    public Marks findByStudentAndSubject(Long id_student, Long id_st) {
        return repository.findByStudentAndSubject(id_student, id_st);
    }

    @Override
    public Marks updateMarks(Marks marks) {
        return repository.save(marks);
    }

    @Override
    public Marks updateMarksNumber(Long id_student, Long id_st, double mark, int number) {
        Marks marks = repository.findByStudentAndSubject(id_student, id_st);
        marks.getMarks().set(number - 1, mark);
        return repository.save(marks);
    }

    @Override
    public void deleteMarks(Long id_student, Long id_st) {
        MarksId id = new MarksId(id_student, id_st);
        repository.deleteById(id);
    }

    @Override
    public void deleteMarksNumber(Long id_student, Long id_st, Long offset) {
        repository.deleteMarksNumber(id_student, id_st, offset - 1);
    }

    @Override
    public void addMarksForGroup(Long group, Long st_id) {
        repository.addMarksForGroup(group, st_id);
    }
}
