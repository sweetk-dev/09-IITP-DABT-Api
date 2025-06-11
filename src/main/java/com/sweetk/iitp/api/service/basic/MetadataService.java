package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.repository.basic.StatsKosisMetadataCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MetadataService {
    
    private final StatsKosisMetadataCodeRepository statMetaCodeRepos;

    @Cacheable(value = "cMetaCodes", key = "#srcDataId + '_' + #statLatestChnDt")
    public List<StatMetaCodeDB> getCatMetaCodes(Integer srcDataId, LocalDate statLatestChnDt) {
        return statMetaCodeRepos.findItemInfoByCatObj(srcDataId, statLatestChnDt);
    }

    @Cacheable(value = "iMetaCodes", key = "#srcDataId + '_' + #statLatestChnDt")
    public List<StatMetaCodeDB> getItemMetaCodes(Integer srcDataId, LocalDate statLatestChnDt) {
        return statMetaCodeRepos.findItemInfoByItemObj(srcDataId, statLatestChnDt);
    }
} 