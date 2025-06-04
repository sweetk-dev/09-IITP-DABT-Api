package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.edu.StatsDisEduVocaExecRepository;
import com.sweetk.iitp.api.repository.basic.edu.StatsDisEduVocaExecWayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 기초-진로 교육 현황 API Service
 */
@Service
@RequiredArgsConstructor
public class BasicEduService extends BasicBaseService {

    private final StatsDisEduVocaExecRepository vocaExecRepos;
    private final StatsDisEduVocaExecWayRepository vocaExecWayRepos;
}
