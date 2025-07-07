package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisJobPostingDto;
import com.sweetk.iitp.api.service.emp.EmpDisJobPostingReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/emp/job-posting")
@RequiredArgsConstructor
public class EmpDisJobPostingController {
    private final EmpDisJobPostingReadService service;

    @GetMapping("")
    public List<EmpDisJobPostingDto> getAll() {
        return service.findAll();
    }
} 