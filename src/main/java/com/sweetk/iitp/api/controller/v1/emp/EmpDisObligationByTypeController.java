package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationByTypeDto;
import com.sweetk.iitp.api.service.emp.EmpDisObligationByTypeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/emp/obligation-by-type")
@RequiredArgsConstructor
public class EmpDisObligationByTypeController {
    private final EmpDisObligationByTypeReadService service;

    @GetMapping("")
    public List<EmpDisObligationByTypeDto> getAll() {
        return service.findAll();
    }
} 