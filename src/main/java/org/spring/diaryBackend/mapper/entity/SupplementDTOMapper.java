package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.model.Supplement;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SupplementDTOMapper implements Function<Supplement, SupplementDTO> {
    @Override
    public SupplementDTO apply(Supplement supplement) {
        return new SupplementDTO(
                supplement.getId(),
                supplement.getComment()
        );
    }
}
