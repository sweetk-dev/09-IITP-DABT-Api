package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisEmpIncentiveDto;
import com.sweetk.iitp.api.service.emp.EmpDisEmpIncentiveReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/emp-incentive")
@RequiredArgsConstructor
public class EmpDisEmpIncentiveController {
    private final EmpDisEmpIncentiveReadService service;

    @GetMapping("")
    public List<EmpDisEmpIncentiveDto> getAll() {
        return service.findAll();
    }
} 