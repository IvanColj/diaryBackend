package org.spring.diaryBackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Embeddable
public class RegularMarks implements Serializable {
    private LocalDate date;
    private Double value;
    @Nullable
    private Integer number;

    private String homework;

    @ManyToOne
    @JoinColumn(name = "type_mark_id")
    private TypeMark typeMark;
}
