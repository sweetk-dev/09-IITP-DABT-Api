package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sweetk.iitp.api.service.emp.EmpDisConsultingReadService;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/consulting")
@RequiredArgsConstructor
public class EmpDisConsultingController {
    private final EmpDisConsultingReadService consultingReadService;
} 