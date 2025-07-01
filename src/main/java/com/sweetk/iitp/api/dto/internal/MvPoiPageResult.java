package com.sweetk.iitp.api.dto.internal;

import com.sweetk.iitp.api.dto.poi.MvPoi;
import java.util.List;

public class MvPoiPageResult {
    public final List<MvPoi> content;
    public final long totalCount;

    public MvPoiPageResult(List<MvPoi> content, long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }
} 