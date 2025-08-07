package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.dto.common.PageReq;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PoiService {

    public DbOffSet setDbOffset(PageReq pageReq) {
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        return new DbOffSet(offset, size);
    }



    record DbOffSet(int offset, int size) {}
}
