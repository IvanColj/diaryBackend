package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.TypeMarkDTO;
import org.spring.diaryBackend.model.TypeMark;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TypeMarkDTOMapper implements Function<TypeMark, TypeMarkDTO> {
    @Override
    public TypeMarkDTO apply(TypeMark typeMark) {
        return new TypeMarkDTO(
                typeMark.getName(),
                typeMark.getWeight()
        );
    }
}
