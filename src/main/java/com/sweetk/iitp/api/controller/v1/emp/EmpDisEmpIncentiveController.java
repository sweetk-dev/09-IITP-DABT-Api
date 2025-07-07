package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisEmpIncentiveDto;
import com.sweetk.iitp.api.service.emp.EmpDisEmpIncentiveReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/emp/emp-incentive")
@RequiredArgsConstructor
public class EmpDisEmpIncentiveController {
    private final EmpDisEmpIncentiveReadService service;

    @GetMapping("")
    public List<EmpDisEmpIncentiveDto> getAll() {
        return service.findAll();
    }
} 