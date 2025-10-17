package com.sweetk.iitp.api.service.openapi;

import com.sweetk.iitp.api.entity.openapi.OpenApiUserEntity;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.openapi.openApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OpenApiWriteService {
    private final openApiRepository openApiRepository;

    public void deleteClient(Long userId, String deletedBy) {


        //@@@ TODO : openapi key 발급시 Client.latestKeyCreatedAt 업데이트 해야함.


        OpenApiUserEntity client = openApiRepository.findClientByUserId(userId).orElse(null);
        if (client == null) {
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, "Client not found");
        }

        openApiRepository.updateClientDeleteStatus(client, deletedBy);
    }
}
