package org.spring.diaryBackend.dbEnum;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    PRESENT("у"),
    EXCUSED_ABSENCE("п"),
    UNEXCUSED_ABSENCE("н"),
    SICK_LEAVE("б");

    private final String code;

    AttendanceStatus(String code) {
        this.code = code;
    }

}