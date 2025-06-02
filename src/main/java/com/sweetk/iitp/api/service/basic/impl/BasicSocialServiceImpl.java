package com.sweetk.iitp.api.service.basic.impl;

import com.sweetk.iitp.api.repository.basic.social.StatsDisSocContactCntfreqRepository;
import com.sweetk.iitp.api.service.basic.BasicBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicSocialServiceImpl extends BasicBaseService {

    private final StatsDisSocContactCntfreqRepository contactCntfreqRepos;
}
