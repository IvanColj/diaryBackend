package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
