package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.TypeMarkDTO;
import org.spring.diaryBackend.mapper.entity.TypeMarkDTOMapper;
import org.spring.diaryBackend.model.TypeMark;
import org.spring.diaryBackend.repository.STRepository;
import org.spring.diaryBackend.repository.TypeMarkRepository;
import org.spring.diaryBackend.service.TypeMarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleTypeMarkService implements TypeMarkService {
    private final TypeMarkRepository typeMarkRepository;
    private final STRepository sTRepository;
    private final TypeMarkDTOMapper typeMarkDTOMapper;

    @Override
    public List<TypeMarkDTO> findAll() {
        return typeMarkRepository.findAll()
                .stream()
                .map(typeMarkDTOMapper)
                .toList();
    }

    @Override
    public List<TypeMarkDTO> findBySt(Long idSt) {
        return typeMarkRepository.findSubjectMarkTypes(idSt)
                .stream()
                .map(typeMarkDTOMapper)
                .toList();
    }

    @Override
    public void save(TypeMarkDTO typeMarkDTO) {
        TypeMark typeMark = new TypeMark();
        typeMark.setName(typeMarkDTO.getName());
        typeMark.setWeight(typeMarkDTO.getWeight());
        typeMark.setIdSt(sTRepository.getReferenceById(typeMarkDTO.getIdSt()));
        typeMarkRepository.save(typeMark);
    }

    @Override
    public void update(TypeMarkDTO typeMarkDTO) {
        TypeMark typeMark = typeMarkRepository.getReferenceById(typeMarkDTO.getId());
        if (typeMarkDTO.getName() != null) {
            typeMark.setName(typeMarkDTO.getName());
        }
        if (typeMarkDTO.getWeight() != null) {
            typeMark.setWeight(typeMarkDTO.getWeight());
        }
        typeMarkRepository.save(typeMark);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        typeMarkRepository.deleteById(id);
    }
}