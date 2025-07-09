package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisBurdenWorkplaceDto;
import com.sweetk.iitp.api.service.emp.EmpDisBurdenWorkplaceReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;


@RestController
@RequestMapping(API_V1_EMP + "/burden-workplace")
@RequiredArgsConstructor
public class EmpDisBurdenWorkplaceController {
    private final EmpDisBurdenWorkplaceReadService service;

    @GetMapping("")
    public List<EmpDisBurdenWorkplaceDto> getAll() {
        return service.findAll();
    }
} 