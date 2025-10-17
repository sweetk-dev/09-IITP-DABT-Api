package com.sweetk.iitp.api.service.openapi;

import com.sweetk.iitp.api.repository.openapi.openApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpenApiReadService {
    private final openApiRepository openApiRepository;
}
