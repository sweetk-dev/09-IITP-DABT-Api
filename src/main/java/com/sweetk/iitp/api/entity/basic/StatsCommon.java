package com.sweetk.iitp.api.entity.basic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public interface StatsCommon {
    Integer getId();
    Integer getSrcDataId();

    Short getPrdDe();

    String getC1();
    String getC2();
    String getC3();

    String getC1ObjNm();
    String getC2ObjNm();
    String getC3ObjNm();

    String getItmId();

    String getUnitNm();

    Object getDt();

    LocalDate getLstChnDe();

    LocalDate getSrcLatestChnDt();

    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    String getCreatedBy();
    String getUpdatedBy();

    default boolean isDtBigDecimal() {
        return getDt() instanceof BigDecimal;
    }

}
