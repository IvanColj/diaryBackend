package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SocialCategoryDTO;
import org.spring.diaryBackend.dto.other.SocialCategoryStatsDTO;
import org.spring.diaryBackend.dto.other.UpdateSocialCategoryDataDTO;
import org.spring.diaryBackend.mapper.entity.SocialCategoryDTOMapper;
import org.spring.diaryBackend.model.SocialCategory;
import org.spring.diaryBackend.repository.SocialCategoryRepository;
import org.spring.diaryBackend.service.SocialCategoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleSocialCategoryService implements SocialCategoryService {
    private final SocialCategoryRepository socialCategoryRepository;
    private final JdbcTemplate jdbcTemplate;
    private final SocialCategoryDTOMapper socialCategoryDTOMapper;

    @Override
    public List<SocialCategoryDTO> findAll() {
        return socialCategoryRepository.findAll()
                .stream()
                .map(socialCategoryDTOMapper)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocialCategoryStatsDTO> getCategoryStats() {
        String sql = """
        SELECT
            sc.name as category_name,
            COUNT(ssc.id_student) as student_count,
            (COUNT(ssc.id_student) * 100.0 / NULLIF((SELECT COUNT(*) FROM student_social_category), 0)) as percentage
        FROM social_category sc
        LEFT JOIN student_social_category ssc ON sc.id = ssc.id_category
        GROUP BY sc.id, sc.name
        ORDER BY student_count DESC;
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> SocialCategoryStatsDTO.builder()
                .categoryName(rs.getString("category_name"))
                .studentsCount(rs.getLong("student_count"))
                .percentage(rs.getDouble("percentage"))
                .build());
    }

    @Override
    public SocialCategory save(SocialCategoryDTO dto) {
        SocialCategory socialCategory = new SocialCategory();
        socialCategory.setName(dto.getName());
        return socialCategoryRepository.save(socialCategory);
    }

    @Override
    public void delete(Long id) {
        socialCategoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateStudentCategoryData(UpdateSocialCategoryDataDTO dto) {
        String sql = """
                UPDATE student_social_category
                SET data = ?::jsonb
                WHERE id_student = ? AND id_category = ?
                """;
        jdbcTemplate.update(sql, dto.getData(), dto.getStudentId(), dto.getCategoryId());
    }
}
