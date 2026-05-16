package org.spring.diaryBackend.service.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentSocialCategoryDTO;
import org.spring.diaryBackend.dto.other.SocialCategoryCountDTO;
import org.spring.diaryBackend.dto.other.StudentBriefDTO;
import org.spring.diaryBackend.service.StudentSocialCategoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SimpleStudentSocialCategoryService implements StudentSocialCategoryService {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public List<StudentBriefDTO> findStudentsByCategory(Long groupId, Long categoryId) {
        String sql = """
        SELECT
            s.id,
            s.last_name || ' ' || s.name || ' ' || s.patronymic as fio
        FROM student s
        JOIN student_social_category ssc ON s.id = ssc.id_student
        WHERE s.id_group = ?
          AND ssc.id_category = ?
        ORDER BY s.last_name ASC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> StudentBriefDTO.builder()
                .id(rs.getLong("id"))
                .fio(rs.getString("fio"))
                .build(), groupId, categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocialCategoryCountDTO> getCategoriesCountForGroup(Long groupId) {
        String sql = """
        SELECT
            sc.id,
            sc.name,
            COUNT(ssc.id_student) as student_count
        FROM social_category sc
        JOIN student_social_category ssc ON sc.id = ssc.id_category
        JOIN student s ON ssc.id_student = s.id
        WHERE s.id_group = ?
        GROUP BY sc.id, sc.name
        ORDER BY student_count DESC;
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> SocialCategoryCountDTO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .count(rs.getLong("student_count"))
                .build(), groupId);
    }

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
