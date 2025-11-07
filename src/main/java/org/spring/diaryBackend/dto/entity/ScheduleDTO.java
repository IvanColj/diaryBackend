package org.spring.diaryBackend.dto.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private Long id;
    private String dayWeek;
    private String typeWeek;
    private Long numPair;
    private String room;
    private Long idSt;
    private Long idGroup;
    private Long subgroup;
    private Boolean replacement;
    private LocalDate dateReplacement;
}
