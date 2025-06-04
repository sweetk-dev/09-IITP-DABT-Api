package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.social.StatsDisSocContactCntfreqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 기초-사회망 현황 API Service
 */
@Service
@RequiredArgsConstructor
public class BasicSocialService extends BasicBaseService {

    private final StatsDisSocContactCntfreqRepository contactCntfreqRepos;
}
