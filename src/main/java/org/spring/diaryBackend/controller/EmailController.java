package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.diaryBackend.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/email")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmailController {
    EmailService emailService;
    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @GetMapping("password/id/{id}/change/{code}")
    public Boolean sendSimpleEmail(@PathVariable("id") Long id, @PathVariable("code") Long code) {
        return emailService.changePassword(id, code);
    }

    @GetMapping("code/active/{id}")
    public void sendCode(@PathVariable("id") Long id) {
        emailService.sendSimpleEmail(id);
    }
}
