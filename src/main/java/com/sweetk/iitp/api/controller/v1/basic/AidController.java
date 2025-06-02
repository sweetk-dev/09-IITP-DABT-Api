package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 기초-보조기기 사용 현황 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_AID)
@RequiredArgsConstructor
@Tag(name = "기초-보조기기 사용 현황 API V1", description = "기초-보조기기 사용 현황 OpenApi V1")
public class AidController extends BasicBaseController {
}
