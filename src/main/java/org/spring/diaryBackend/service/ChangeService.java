package org.spring.diaryBackend.service;

import org.spring.diaryBackend.model.Change;

import java.util.List;

public interface ChangeService {
    List<Change> findAllChange();
}
