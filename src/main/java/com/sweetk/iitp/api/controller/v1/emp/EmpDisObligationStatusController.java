package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationStatusDto;
import com.sweetk.iitp.api.service.emp.EmpDisObligationStatusReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/obligation-status")
@RequiredArgsConstructor
public class EmpDisObligationStatusController {
    private final EmpDisObligationStatusReadService service;

    @GetMapping("")
    public List<EmpDisObligationStatusDto> getAll() {
        return service.findAll();
    }
} 