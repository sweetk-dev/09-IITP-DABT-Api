package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sweetk.iitp.api.service.emp.EmpDisTrainingCourseReadService;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

@RestController
@RequestMapping(API_V1_EMP + "/training-course")
@RequiredArgsConstructor
public class EmpDisTrainingCourseController {
    private final EmpDisTrainingCourseReadService trainingCourseReadService;
} 