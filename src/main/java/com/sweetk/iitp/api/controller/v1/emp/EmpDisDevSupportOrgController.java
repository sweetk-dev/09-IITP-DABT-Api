package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sweetk.iitp.api.service.emp.EmpDisDevSupportOrgReadService;

@RestController
@RequestMapping("/api/v1/emp/dev-support-org")
@RequiredArgsConstructor
public class EmpDisDevSupportOrgController {
    private final EmpDisDevSupportOrgReadService devSupportOrgReadService;
} 