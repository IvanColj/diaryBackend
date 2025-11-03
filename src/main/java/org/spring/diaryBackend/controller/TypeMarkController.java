package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.TypeMarkDTO;
import org.spring.diaryBackend.service.TypeMarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/typeMarks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TypeMarkController {
    private final TypeMarkService typeMarkService;

    @GetMapping()
    public List<TypeMarkDTO> findAll() {
        return typeMarkService.findAll();
    }

    @GetMapping("st/{idSt}")
    public List<TypeMarkDTO> findBySt(@PathVariable("idSt") Long idSt) {
        return typeMarkService.findBySt(idSt);
    }

    @PostMapping("save")
    public void save(@RequestBody TypeMarkDTO typeMarkDTO) {
        typeMarkService.save(typeMarkDTO);
    }

    @PostMapping("update")
    public void update(@RequestBody TypeMarkDTO typeMarkDTO) {
        typeMarkService.update(typeMarkDTO);
    }

    @GetMapping("id/{id}")
    public void delete(@PathVariable("id") Long id) {
        typeMarkService.delete(id);
    }
}
