package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.Teacher;
import org.spring.diaryBackend.repository.TeacherRepository;
import org.spring.diaryBackend.service.TeacherService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleTeacherService implements TeacherService {
    private final TeacherRepository repository;
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    @Override
    public List<Teacher> findByAllTeacher(int offset, int limit) {
        return repository.findByAllTeacher(offset, limit);
    }

    @Override
    public List<Teacher> findAllTeacher() {
        return repository.findAll();
    }

    @Override
    public Teacher findTeacherById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        teacher.setPassword(encoder.encode(teacher.getPassword()));
        return repository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        teacher.setPassword(encoder.encode(teacher.getPassword()));
        return repository.save(teacher);
    }

    @Override
    public Teacher findByLoginOrPassword(String login, String password) {
        Teacher teacher = repository.findByLoginOrPassword(login, password);
        if (teacher != null && encoder.matches(password, teacher.getPassword())) {
            return teacher;
        }
        else {
            return new Teacher();
        }
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        repository.deleteById(id);
    }
}
