package org.spring.diaryBackend.service;

import org.spring.diaryBackend.model.SemesterMarks;

import java.util.List;

public interface MarksService {
    List<SemesterMarks> findByAllMarks(int offset, int limit);
    List<SemesterMarks> findAllMarks();
    List<SemesterMarks> findByStudentMarks(Long student);
    List<SemesterMarks> findByObjectMarks(Long object);

    void addMarksForGroup(Long group, Long st_id);
    SemesterMarks findByStudentAndSubject(Long id_student, Long id_st);
    SemesterMarks saveMarks(SemesterMarks semesterMarks);
    void saveMark(Long id_student, Long id_st, double mark);
    SemesterMarks updateMarks(SemesterMarks semesterMarks);
    SemesterMarks updateMarksNumber(Long id_student, Long id_st, Double markValue, int number);
    void deleteMarksNumber(Long id_student, Long id_st, Long offset);
    void deleteMarks(Long id_student, Long id_st);

}
