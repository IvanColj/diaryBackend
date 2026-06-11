package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentCertificationGroupDTO {
    private Long idSt;
    private String nameSubject;
    private String certificationType;
    private Long semester;
}
