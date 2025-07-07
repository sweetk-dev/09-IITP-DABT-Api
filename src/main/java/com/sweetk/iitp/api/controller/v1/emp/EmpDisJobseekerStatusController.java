package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sweetk.iitp.api.service.emp.EmpDisJobseekerReadService;

@RestController
@RequestMapping("/api/v1/emp/jobseeker-status")
@RequiredArgsConstructor
public class EmpDisJobseekerStatusController {
    private final EmpDisJobseekerReadService jobseekerReadService;
} 