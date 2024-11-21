package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.GroupMarksDTO;
import org.spring.diaryBackend.model.Group;
import java.util.List;

public interface GroupService {

    List<Group> findByAllGroup(int offset, int limit);
    List<Group> findAll();
    List<GroupMarksDTO> getGroupMarks(Long numberGroup);
    Group findGroupByNumberGroup(Long numberGroup);
    Group saveGroup(Group group);
    Group updateGroup(Group group);
    void deleteNumberGroup(Long numberGroup);
}
