package org.spring.diaryBackend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class getCurrentCertificationGroupDTO {
    private Long idSt;
    private String nameSubject;
    private String certificationType;
}
