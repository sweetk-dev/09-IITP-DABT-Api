package com.sweetk.iitp.api.dto.internal;

import com.sweetk.iitp.api.dto.basic.StatDataItem;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class StatDataItemDB {
    private Short prdDe;
    private String c1;        // 코드값
    private String c2;        // 코드값
    private String c3;        // 코드값

    private String itmId;     // 코드값

    private String unitNm;
    private String data;
    private LocalDate lstChnDe;
    private Integer srcDataId;


    private String c1Nm;
    private String c2Nm;
    private String c3Nm;

    private String itmNm;

    private String c1ObjId;
    private String c2ObjId;
    private String c3ObjId;

    private String c1ObjNm;
    private String c2ObjNm;
    private String c3ObjNm;

    private String itmObjId;
    private String itmObjNm;


    public StatDataItem toStatDataItem() {
        return StatDataItem.builder()
                .prdDe(this.prdDe)
                .c1Nm(this.c1Nm)
                .c2Nm(this.c2Nm)
                .c3Nm(this.c3Nm)
                .itmNm(this.itmNm)
                .unitNm(this.unitNm)
                .data(this.data)
                .lstChnDe(this.lstChnDe)
                .build();
    }

    public StatDataItemDB(
            Short prdDe,
            String c1,
            String c2,
            String c3,
            String itmId,
            String unitNm,
            String data,
            LocalDate lstChnDe,
            Integer srcDataId
    ) {
        this.prdDe = prdDe;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.itmId = itmId;
        this.unitNm = unitNm;
        this.data = data;
        this.lstChnDe = lstChnDe;
        this.srcDataId = srcDataId;
    }
} 