package org.spring.diaryBackend.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Embeddable
public class SemesterMarksId implements Serializable {
    private Long idStudent;
    private Long idSt;
}
