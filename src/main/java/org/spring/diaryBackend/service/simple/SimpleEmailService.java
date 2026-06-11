package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.repository.StudentRepository;
import org.spring.diaryBackend.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
public class SimpleEmailService implements EmailService {
    private final JavaMailSender emailSender;
    private final StudentRepository studentRepository;

    @Override
    public Boolean changePassword(Long id, Long code) {
        Student student = studentRepository.findById(id).orElse(null);
        if (Objects.equals(Objects.requireNonNull(student).getCode(), code)) {
            student.setCode(0L);
            studentRepository.save(student);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void sendSimpleEmail(Long id) {
        Random x = new Random();
        Long code = x.nextLong(999999);

        Student student = studentRepository.findById(id).orElse(null);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(Objects.requireNonNull(student).getEmail());
        simpleMailMessage.setFrom("robskovv@gmail.com");
        simpleMailMessage.setSubject("Смена пароля в дневнике НовГу");
        simpleMailMessage.setText(code + " код для смены пароля");
        student.setCode(code);
        studentRepository.save(student);
        emailSender.send(simpleMailMessage);
    }
}
