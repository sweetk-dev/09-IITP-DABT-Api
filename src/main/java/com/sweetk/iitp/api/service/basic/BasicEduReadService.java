package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.edu.EduRepository;
import com.sweetk.iitp.api.repository.basic.edu.StatsDisEduVocaExecRepository;
import com.sweetk.iitp.api.repository.basic.edu.StatsDisEduVocaExecWayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기초-진로 교육 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicEduReadService extends AbstractBasicService {

    private final EduRepository eduRepository;
    private final MetadataService metadataService;
    private final DataSourceService dataSourceService;

    /*******************************
     * 장애인 진로 및 직업교육 실시 여부
     *******************************/
    edu_voca_exec




            /*******************************
             * 장애인 진로 및 직업교육 운영 방법
             *******************************/
            edu_voca_exec_way
}
