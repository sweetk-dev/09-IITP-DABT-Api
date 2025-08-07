package com.sweetk.iitp.api.dto.internal;

import java.util.List;

public class PoiPageResult<T> {
    public final List<T> content;
    public final long totalCount;

    public PoiPageResult(List<T> content, long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }
    
    public List<T> getContent() {
        return content;
    }
    
    public long getTotalCount() {
        return totalCount;
    }
} 