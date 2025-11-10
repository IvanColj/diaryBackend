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

    @Override
    public String toString() {
        return code;
    }

    public static String toCode(String statusNameOrCode) {
        if (statusNameOrCode == null) return null;

        if (statusNameOrCode.length() == 1 && "упнб".contains(statusNameOrCode)) {
            return statusNameOrCode;
        }

        try {
            return valueOf(statusNameOrCode).getCode();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}