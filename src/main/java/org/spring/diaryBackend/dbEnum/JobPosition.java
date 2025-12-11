package org.spring.diaryBackend.dbEnum;

import lombok.Getter;

@Getter
public enum JobPosition {
    DIRECTOR_OF_TPC("Директор ПТК"),
    DEPUTY_DIRECTOR_EDUCATIONAL_WORK("Зам. директора по УМ и ВР"),
    DEPUTY_DIRECTOR_ADMINISTRATIVE_WORK("Зам. директора по УПР"),
    HEAD_OF_ACADEMIC_DEPARTMENT("Зав. учебной частью"),
    METHODOLOGIST("методист"),
    HEAD_OF_DEPARTMENT("Зав. отделением"),
    ADDITIONAL_EDUCATION_TEACHER("Педагог доп. образования"),
    EDUCATIONAL_PSYCHOLOGIST("Педагог-психолог"),
    TEACHER("Преподаватель"),
    SOCIAL_TEACHER("соц. педагог");

    private final String label;

    JobPosition(String label) {
        this.label = label;
    }

}
