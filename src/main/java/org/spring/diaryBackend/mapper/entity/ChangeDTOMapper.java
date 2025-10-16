package org.spring.diaryBackend.mapper.entity;

import lombok.RequiredArgsConstructor;
import org.spring.diaryBackend.dto.entity.ChangeDTO;
import org.spring.diaryBackend.model.Change;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ChangeDTOMapper implements Function<Change, ChangeDTO> {
    @Override
    public ChangeDTO apply(Change change) {
        return new ChangeDTO(
                change.getId(),
                change.getDateTime(),
                change.getAction(),
                change.getIdSupplement() != null ?
                        change.getIdSupplement().getId() : null,
                change.getTeacherOrStudent(),
                change.getOldValue()
        );
    }
}
