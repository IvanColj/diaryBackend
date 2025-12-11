package org.spring.diaryBackend.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DelMarksGroup {
    Long number;
    Long idSt;
    List<Long> students;
}
