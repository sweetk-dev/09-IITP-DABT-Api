package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sweetk.iitp.api.service.emp.EmpDisDevSupportOrgReadService;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/dev-support-org")
@RequiredArgsConstructor
public class EmpDisDevSupportOrgController {
    private final EmpDisDevSupportOrgReadService devSupportOrgReadService;
} 