package org.spring.diaryBackend.service.impl;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.GroupMarksDTO;
import org.spring.diaryBackend.model.Group;
import org.spring.diaryBackend.repository.GroupRepository;
import org.spring.diaryBackend.service.GroupService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class SimpleGroupService implements GroupService {
    private final GroupRepository repository;

    @Override
    public List<Group> findByAllGroup(int offset, int limit) {
        return repository.findByAllGroup(offset, limit);
    }

    @Override
    public List<Group> findAll() {
        return repository.findAll();
    }

    @Override
    public Group findGroupByNumberGroup(Long numberGroup) {
        return repository.findGroupByNumberGroup(numberGroup);
    }

    @Override
    public List<Long> findBySubject(Long group) {
        return repository.findBySubject(group);
    }

    @Override
    public List<GroupMarksDTO> getGroupMarks(Long numberGroup, Long subject) {
        List<GroupMarksDTO> baseInfoList = repository.findBaseInfo(numberGroup);
        List<Object[]> rawMarks = repository.findAllMarksGroup(numberGroup, subject);

        Map<Long, List<Double>> marksByStudentId = rawMarks.stream()
                .collect(Collectors.groupingBy(
                        row -> (Long) row[0],
                        Collectors.mapping(
                                row -> (Double) row[1],
                                Collectors.toList()
                        )
                ));
        for (GroupMarksDTO student : baseInfoList) {
            Map<Long, List<Double>> studentMarksMap = new HashMap<>();
            studentMarksMap.put(student.getId_student(), marksByStudentId.getOrDefault(student.getId_student(), new ArrayList<>()));
            student.setMarks(studentMarksMap.get(student.getId_student()));
        }

        return baseInfoList;
    }

    @Override
    public Group saveGroup(Group group) {
        return repository.save(group);
    }

    @Override
    public Group updateGroup(Group group) {
        return repository.save(group);
    }

    @Override
    @Transactional
    public void deleteNumberGroup(Long numberGroup) {
        repository.deleteNumberGroup(numberGroup);
    }
}
