package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisStdWorkplaceDto;
import com.sweetk.iitp.api.service.emp.EmpDisStdWorkplaceReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/emp/std-workplace")
@RequiredArgsConstructor
public class EmpDisStdWorkplaceController {
    private final EmpDisStdWorkplaceReadService service;

    @GetMapping("")
    public List<EmpDisStdWorkplaceDto> getAll() {
        return service.findAll();
    }
} 