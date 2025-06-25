package com.sweetk.iitp.api.service.client;

import com.sweetk.iitp.api.repository.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientReadService {
    private final ClientRepository clientRepository;
}
