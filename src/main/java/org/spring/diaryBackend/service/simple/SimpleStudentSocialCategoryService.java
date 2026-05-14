package org.spring.diaryBackend.service.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentSocialCategoryDTO;
import org.spring.diaryBackend.service.StudentSocialCategoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class SimpleStudentSocialCategoryService implements StudentSocialCategoryService {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void save(StudentSocialCategoryDTO dto) {
        String sql = "INSERT INTO student_social_category (id_student, id_category, data) VALUES (?, ?, ?::jsonb)";

        String jsonString = convertMapToJson(dto.getData());
        jdbcTemplate.update(sql, dto.getIdStudent(), dto.getIdCategory(), jsonString);
    }

    @Override
    @Transactional
    public void update(StudentSocialCategoryDTO dto) {
        String sql = "UPDATE student_social_category SET data = ?::jsonb WHERE id_student = ? AND id_category = ?";

        String jsonString = convertMapToJson(dto.getData());
        jdbcTemplate.update(sql, jsonString, dto.getIdStudent(), dto.getIdCategory());
    }

    @Override
    @Transactional
    public void delete(Long idStudent, Long idCategory) {
        String sql = "DELETE FROM student_social_category WHERE id_student = ? AND id_category = ?";
        jdbcTemplate.update(sql, idStudent, idCategory);
    }

    private String convertMapToJson(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при конвертации данных в JSON", e);
        }
    }
}
