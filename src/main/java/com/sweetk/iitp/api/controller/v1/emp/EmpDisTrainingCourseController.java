package com.sweetk.iitp.api.controller.v1.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sweetk.iitp.api.service.emp.EmpDisTrainingCourseReadService;

@RestController
@RequestMapping("/api/v1/emp/training-course")
@RequiredArgsConstructor
public class EmpDisTrainingCourseController {
    private final EmpDisTrainingCourseReadService trainingCourseReadService;
} 