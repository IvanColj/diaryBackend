package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.UpdateMarkDTO;
import org.spring.diaryBackend.logic.DelMarksGroup;
import org.spring.diaryBackend.model.SemesterMarks;

import java.util.List;

public interface MarksService {
    List<SemesterMarks> findAllMarks();
    List<SemesterMarks> findByStudentMarks(Long student);
    List<SemesterMarks> findByObjectMarks(Long object);

    void addMarksForGroup(Long group, Long st_id);
    SemesterMarks findByStudentAndSubject(Long id_student, Long id_st);
    SemesterMarks updateMarks(SemesterMarks semesterMarks);
    SemesterMarks updateMarksNumber(UpdateMarkDTO updateMarkDTO);

    void deleteMarksNumberGroupST(Long number, Long group, Long st);
    void deleteMarksGroupSt(DelMarksGroup delMarksGroup);
    void deleteMarksNumber(Long id_student, Long id_st, Long number);
    void deleteMarks(Long id_student, Long id_st);

}
