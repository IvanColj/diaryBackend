package org.spring.diaryBackend.dto.other;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.diaryBackend.dbEnum.AttendanceStatus;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceStudentDTO {
    private Long idLesson;
    private LocalDate date;
    private String status;
    private String comment;

    public AttendanceStudentDTO(Long lessonId, LocalDate date, AttendanceStatus status, String comment) {
        this.idLesson = lessonId;
        this.date = date;
        this.status = status != null ? status.getCode() : null;
        this.comment = comment;
    }
}
