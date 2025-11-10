package org.spring.diaryBackend.dto.other;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
