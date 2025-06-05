package com.sweetk.iitp.api.dto.internal;

import com.sweetk.iitp.api.dto.basic.StatDataItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
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

    public StatDataItem toStatDataItem() {
        return StatDataItem.builder()
                .prdDe(this.prdDe)
                .c1(this.c1)
                .c2(this.c2)
                .c3(this.c3)
                .c1ObjNm(this.c1ObjNm)
                .c2ObjNm(this.c2ObjNm)
                .c3ObjNm(this.c3ObjNm)
                .itmId(this.itmId)
                .unitNm(this.unitNm)
                .data(this.data)
                .lstChnDe(this.lstChnDe)
                .build();
    }
} 