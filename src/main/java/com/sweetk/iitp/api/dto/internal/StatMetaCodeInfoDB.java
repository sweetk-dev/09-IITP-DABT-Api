package com.sweetk.iitp.api.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatMetaCodeInfoDB {
    String itemId;
    String itemNm;
    String objId;
    String objNm;

    public StatMetaCodeInfoDB(String itemId, String itemNm, String objId, String objNm) {
        this.itemId = itemId;
        this.itemNm = itemNm;
        this.objId = objId;
        this.objNm = objNm;
    }
}
