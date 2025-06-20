package com.sweetk.iitp.api.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatMetaCodeDB {
    String itemId;
    String itemNm;
    String objNm;

    public StatMetaCodeDB(String itemId, String itemNm, String objNm) {
        this.itemId = itemId;
        this.itemNm = itemNm;
        this.objNm = objNm;
    }
}
