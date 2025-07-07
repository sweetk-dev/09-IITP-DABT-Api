package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sweetk.iitp.api.service.emp.EmpDisEntreLectureReadService;

@RestController
@RequestMapping("/api/v1/emp/entre-lecture")
@RequiredArgsConstructor
public class EmpDisEntreLectureController {
    private final EmpDisEntreLectureReadService entreLectureReadService;
} 