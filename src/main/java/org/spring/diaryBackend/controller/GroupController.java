package org.spring.diaryBackend.controller;

import lombok.RequiredArgsConstructor;
import org.spring.diaryBackend.dto.GroupMarksDTO;
import org.spring.diaryBackend.model.Group;
import org.spring.diaryBackend.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService service;

    @GetMapping
    public List<Group> getByAllGroups(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "5") int limit) {
        return service.findByAllGroup(offset, limit);
    }

    @GetMapping("all")
    public List<Group> getAllGroups() {
        return service.findAll();
    }

    @GetMapping("number/{group}")
    public Group getByNumber(@PathVariable Long group) {
        return service.findGroupByNumberGroup(group);
    }

    @GetMapping("marks/group/{group}/subject/{subject}")
    public List<GroupMarksDTO> findGroupMarks(@PathVariable Long group, @PathVariable Long subject) {
        return service.getGroupMarks(group, subject);
    }

    @PostMapping("save_group")
    public Group createGroup(@RequestBody Group group) {
        return service.saveGroup(group);
    }

    @PutMapping("update_group")
    public Group updateGroup(@RequestBody Group group) {
        return service.updateGroup(group);
    }

    @DeleteMapping("delete_group/{group}")
    public void deleteNumberGroup(@PathVariable Long group) {
        service.deleteNumberGroup(group);
    }
}
