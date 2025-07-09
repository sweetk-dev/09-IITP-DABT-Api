package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisIndustryObligationDto;
import com.sweetk.iitp.api.service.emp.EmpDisIndustryObligationReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/industry-obligation")
@RequiredArgsConstructor
public class EmpDisIndustryObligationController {
    private final EmpDisIndustryObligationReadService service;

    @GetMapping("")
    public List<EmpDisIndustryObligationDto> getAll() {
        return service.findAll();
    }
} 