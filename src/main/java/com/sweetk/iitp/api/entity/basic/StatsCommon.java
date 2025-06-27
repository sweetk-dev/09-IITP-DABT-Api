package com.sweetk.iitp.api.entity.basic;

import com.sweetk.iitp.api.constant.SysConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;


public interface StatsCommon {
    Integer getId();
    Integer getSrcDataId();

    Short getPrdDe();

    String getC1();
    String getC2();
    String getC3();

//    String getC1ObjNm();
//    String getC2ObjNm();
//    String getC3ObjNm();

    String getItmId();

    String getUnitNm();

    Object getDt();

    LocalDate getLstChnDe();

    LocalDate getSrcLatestChnDt();

    OffsetDateTime getCreatedAt();
    OffsetDateTime getUpdatedAt();
    OffsetDateTime getDeletedAt();

    String getCreatedBy();
    String getUpdatedBy();
    String getDeletedBy();

    String getDelYn();

    default boolean isDtBigDecimal() {
        return getDt() instanceof BigDecimal;
    }

    default boolean isDeleted() {
        return SysConstants.YN_Y.equals(getDelYn()) || getDeletedAt() != null;
    }
}
