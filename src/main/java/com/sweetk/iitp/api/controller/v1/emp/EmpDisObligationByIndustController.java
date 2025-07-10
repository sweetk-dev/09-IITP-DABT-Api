package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationByIndustDto;
import com.sweetk.iitp.api.service.emp.EmpDisObligationByIndustReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/industry-obligation")
@RequiredArgsConstructor
public class EmpDisObligationByIndustController {
    private final EmpDisObligationByIndustReadService service;

    @GetMapping("")
    public List<EmpDisObligationByIndustDto> getAll() {
        return service.findAll();
    }
} 