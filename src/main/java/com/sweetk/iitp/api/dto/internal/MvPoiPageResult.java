package com.sweetk.iitp.api.dto.internal;

import java.util.List;

public class MvPoiPageResult<T> {
    public final List<T> content;
    public final long totalCount;

    public MvPoiPageResult(List<T> content, long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }
    
    public List<T> getResults() {
        return content;
    }
    
    public long getTotalCount() {
        return totalCount;
    }
} 