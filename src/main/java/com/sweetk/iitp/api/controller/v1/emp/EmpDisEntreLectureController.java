package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sweetk.iitp.api.service.emp.EmpDisEntreLectureReadService;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/entre-lecture")
@RequiredArgsConstructor
public class EmpDisEntreLectureController {
    private final EmpDisEntreLectureReadService entreLectureReadService;
} 