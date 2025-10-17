package com.sweetk.iitp.api.controller.v1.openapi;


import com.sweetk.iitp.api.constant.ApiConstants;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_MGMT_OPENAPI)
@RequiredArgsConstructor
public class OpenApiController {
}
