package com.sweetk.iitp.api.service.basic.impl;

import com.sweetk.iitp.api.repository.basic.edu.StatsDisEduVocaExecRepository;
import com.sweetk.iitp.api.repository.basic.edu.StatsDisEduVocaExecWayRepository;
import com.sweetk.iitp.api.service.basic.BasicBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicEduServiceImpl extends BasicBaseService {

    private final StatsDisEduVocaExecRepository vocaExecRepos;
    private final StatsDisEduVocaExecWayRepository vocaExecWayRepos;
}
