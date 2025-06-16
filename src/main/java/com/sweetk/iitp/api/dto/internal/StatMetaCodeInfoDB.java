package com.sweetk.iitp.api.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StatMetaCodeInfoDB {
    String itemId;
    String itemNm;
    String objId;
    String objNm;
}
