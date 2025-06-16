package com.sweetk.iitp.api.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StatMetaCodeDB {
    String itemId;
    String itemNm;
    String objNm;
}
