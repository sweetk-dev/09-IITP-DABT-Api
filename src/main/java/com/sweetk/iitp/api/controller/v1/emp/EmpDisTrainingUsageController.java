package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisTrainingUsageDto;
import com.sweetk.iitp.api.service.emp.EmpDisTrainingUsageReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/emp/training-usage")
@RequiredArgsConstructor
public class EmpDisTrainingUsageController {
    private final EmpDisTrainingUsageReadService service;

    @GetMapping("")
    public List<EmpDisTrainingUsageDto> getAll() {
        return service.findAll();
    }
} 