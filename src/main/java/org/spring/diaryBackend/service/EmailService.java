package org.spring.diaryBackend.service;

public interface EmailService {
    Boolean changePassword(Long id, Long code);
    void sendSimpleEmail(Long id);
}
