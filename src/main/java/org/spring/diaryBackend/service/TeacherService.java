package org.spring.diaryBackend.service;

import org.spring.diaryBackend.model.Subject;
import org.spring.diaryBackend.model.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findByAllTeacher(int offset, int limit);
    List<Teacher> findAllTeacher();

    List<Subject> findByAllSubject(Long id);
    Teacher findTeacherById(Long id);
    Teacher saveTeacher(Teacher teacher);
    Teacher updateTeacher(Teacher teacher);
    Teacher findByLoginOrPassword(String login, String password);
    void deleteTeacher(Long id);

}
