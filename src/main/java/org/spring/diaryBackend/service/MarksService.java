package org.spring.diaryBackend.service;

import org.spring.diaryBackend.model.Marks;

import java.util.List;

public interface MarksService {
    List<Marks> findByAllMarks(int offset, int limit);
    List<Marks> findAllMarks();
    List<Marks> findByStudentMarks(Long student);
    List<Marks> findByObjectMarks(Long object);

    void addMarksForGroup(Long group, Long st_id);
    Marks findByStudentAndSubject(Long id_student, Long id_st);
    Marks saveMarks(Marks marks);
    void saveMark(Long id_student, Long id_st, double mark);
    Marks updateMarks(Marks marks);
    Marks updateMarksNumber(Long id_student, Long id_st, double mark, int number);
    void deleteMarksNumber(Long id_student, Long id_st, Long offset);
    void deleteMarks(Long id_student, Long id_st);

}
