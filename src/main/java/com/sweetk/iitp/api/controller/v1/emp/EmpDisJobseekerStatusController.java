package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.service.emp.EmpDisJobseekerStatusReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;


//01. 장애인 구직자 현황
@RestController
@RequestMapping(API_V1_EMP + "/jobseeker-status")
@RequiredArgsConstructor
public class EmpDisJobseekerStatusController {
    private final EmpDisJobseekerStatusReadService jobseekerStatusReadService;



} 