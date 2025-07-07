package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sweetk.iitp.api.service.emp.EmpDisConsultingReadService;

@RestController
@RequestMapping("/api/v1/emp/consulting")
@RequiredArgsConstructor
public class EmpDisConsultingController {
    private final EmpDisConsultingReadService consultingReadService;
} 