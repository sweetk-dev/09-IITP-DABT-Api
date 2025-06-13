package com.sweetk.iitp.api.dto.internal;

import com.sweetk.iitp.api.dto.basic.StatDataItem;
import lombok.*;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
public class StatDataItemDB {
    private Short prdDe;
    private String c1;        // 코드값
    private String c2;        // 코드값
    private String c3;        // 코드값

    private String c1ObjNm;
    private String c2ObjNm;
    private String c3ObjNm;

    private String itmId;     // 코드값

    private String unitNm;
    private String data;
    private LocalDate lstChnDe;
    private Integer srcDataId;


    private String c1Nm;
    private String c2Nm;
    private String c3Nm;

    private String itmNm;



    public StatDataItem toStatDataItem() {
        return StatDataItem.builder()
                .prdDe(this.prdDe)
                .c1Nm(this.c1Nm)
                .c2Nm(this.c2Nm)
                .c3Nm(this.c3Nm)
                .c1ObjNm(this.c1ObjNm)
                .c2ObjNm(this.c2ObjNm)
                .c3ObjNm(this.c3ObjNm)
                .itmNm(this.itmNm)
                .unitNm(this.unitNm)
                .data(this.data)
                .lstChnDe(this.lstChnDe)
                .build();
    }
} 