package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.entity.TypeMarkDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TypeMarkSTDTOMapper implements Function<Object[], TypeMarkDTO> {
    @Override
    public TypeMarkDTO apply(Object[] objects) {
        return new TypeMarkDTO(
                (Long) objects[0],
                (Long) objects[1],
                (String) objects[2],
                (Long) objects[3]
        );
    }
}
