package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisTrainingUsageDto;
import com.sweetk.iitp.api.service.emp.EmpDisTrainingUsageReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/training-usage")
@RequiredArgsConstructor
public class EmpDisTrainingUsageController {
    private final EmpDisTrainingUsageReadService service;

    @GetMapping("")
    public List<EmpDisTrainingUsageDto> getAll() {
        return service.findAll();
    }
} 