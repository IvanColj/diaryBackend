package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.GroupMarksDTO;
import org.spring.diaryBackend.model.Group;
import org.spring.diaryBackend.model.Student;

import java.io.IOException;
import java.util.List;

public interface GroupService {

    List<Group> findByAllGroup(int offset, int limit);
    List<Group> findAll();
    List<GroupMarksDTO> getGroupMarks(Long numberGroup, Long subject);
    List<Long> findBySubject(Long group);
    List<Student> fetchStudentsGroup(Long groupNumber) throws IOException;
    Group findGroupByNumberGroup(Long numberGroup);
    Group saveGroup(Group group);
    Group updateGroup(Group group);
    void deleteNumberGroup(Long numberGroup);
}
