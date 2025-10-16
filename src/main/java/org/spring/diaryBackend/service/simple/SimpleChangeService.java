package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.repository.ChangeRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleChangeService {
    private final ChangeRepository changeRepository;
}
