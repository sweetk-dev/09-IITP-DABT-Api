package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.social.StatsDisSocContactCntfreqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기초-사회망 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicSocialReadService extends BasicCommLogic {

    private final StatsDisSocContactCntfreqRepository contactCntfreqRepos;
}
