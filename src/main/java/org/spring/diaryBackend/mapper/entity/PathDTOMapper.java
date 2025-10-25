package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.PathDTO;
import org.spring.diaryBackend.model.Path;
import org.spring.diaryBackend.model.Staff;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PathDTOMapper implements Function<Path, PathDTO> {
    @Override
    public PathDTO apply(Path path) {
        return new PathDTO(
                path.getId(),
                path.getNameFile(),
                path.getPathToFile(),
                path.getIdStudent(),
                path.getAccessTeacher(),
                path.getType(),
                path.getStaffs().stream().map(Staff::getId).toList()
        );
    }
}
