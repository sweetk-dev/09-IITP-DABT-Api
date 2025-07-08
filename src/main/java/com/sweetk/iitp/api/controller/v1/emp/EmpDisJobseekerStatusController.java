package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.service.emp.EmpDisJobseekerStatusReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emp/jobseeker-status")
@RequiredArgsConstructor
public class EmpDisJobseekerStatusController {
    private final EmpDisJobseekerStatusReadService jobseekerStatusReadService;
} 