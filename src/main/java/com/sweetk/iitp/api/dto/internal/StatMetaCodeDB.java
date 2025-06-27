package com.sweetk.iitp.api.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatMetaCodeDB {
    String itemId;
    String itemNm;
    String objId;
    String objNm;

    public StatMetaCodeDB(String itemId, String itemNm, String objId, String objNm) {
        this.itemId = itemId;
        this.itemNm = itemNm;
        this.objId = objId;
        this.objNm = objNm;
    }
}
