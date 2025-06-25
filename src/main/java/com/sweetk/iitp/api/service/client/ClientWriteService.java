package com.sweetk.iitp.api.service.client;

import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientWriteService {
    private final ClientRepository clientRepository;

    public void deleteClient(Integer clientId, String deletedBy) {

        OpenApiClientEntity client = clientRepository.findClientByApiCliId(clientId).orElse(null);
        if (client == null) {
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, "Client not found");
        }

        clientRepository.updateClientDeleteStatus(client, deletedBy);
    }
}
