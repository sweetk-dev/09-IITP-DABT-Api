package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationFulfillmentDto;
import com.sweetk.iitp.api.service.emp.EmpDisObligationFulfillmentReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/emp/obligation-fulfillment")
@RequiredArgsConstructor
public class EmpDisObligationFulfillmentController {
    private final EmpDisObligationFulfillmentReadService service;

    @GetMapping("")
    public List<EmpDisObligationFulfillmentDto> getAll() {
        return service.findAll();
    }
} 