package org.spring.diaryBackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Marks {
    @EmbeddedId
    private MarksId id;
    @Nullable
    private Double certification;
    @ElementCollection
    private List<Double> marks;
}
