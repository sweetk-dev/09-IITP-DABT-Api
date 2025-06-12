package com.sweetk.iitp.api.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {
    /**
     * LocalDateTime을 java.util.Date로 변환합니다.
     * 시스템 기본 시간대(ZoneId.systemDefault()) 기준입니다.
     * 사용 예) Date date = DateTimeUtils.toUtilDate(now); // 시스템 기본 시간대 기준
     */
    public static Date toUtilDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime을 java.util.Date로 변환합니다.
     * 명시된 시간대 기준입니다.
     * 사용 예)  Date utcDate = DateTimeUtils.toUtilDate(now, ZoneId.of("UTC")); // UTC 기준
     */
    public static Date toUtilDate(LocalDateTime localDateTime, ZoneId zoneId) {
        if (localDateTime == null || zoneId == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }
}

