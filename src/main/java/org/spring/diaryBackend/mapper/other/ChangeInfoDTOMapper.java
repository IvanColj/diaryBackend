package org.spring.diaryBackend.mapper.other;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.other.ChangeInfoDTO;
import org.spring.diaryBackend.model.Change;
import org.spring.diaryBackend.repository.SupplementRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class ChangeInfoDTOMapper implements Function<Change, ChangeInfoDTO> {
    private final SupplementRepository supplementRepository;
    @Override
    public ChangeInfoDTO apply(Change change) {
        return new ChangeInfoDTO(
                change.getId(),
                change.getDateTime(),
                change.getAction(),
                change.getIdSupplement() != null ?
                change.getIdSupplement().getId() : null,
                change.getIdSupplement() != null ?
                change.getIdSupplement().getComment() : null,
                change.getIdSupplement() != null ?
                supplementRepository.findAllFilesSupplement(change.getIdSupplement().getId()) : null,
                change.getTeacherOrStudent(),
                change.getNewValue()
        );
    }
}
