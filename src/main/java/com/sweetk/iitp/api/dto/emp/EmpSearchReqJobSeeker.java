package com.sweetk.iitp.api.dto.emp;

import com.sweetk.iitp.api.constant.emp.empJopEmpType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "구직자 정보 검색 요청")
public class EmpSearchReqJobSeeker {
    @Schema(description = "급여 형태(옵션)",
            example = "hourly",
            allowableValues = {"hourly", "monthly"})
    private empJopEmpType salaryType;


    @Schema(description = "업종(2글자 이상) (옵션)",
            example = "세탁원")
    @Size(min = 2)
    private String jobType;


    @Schema(description = "희망 지역(2글자 이상) (옵션)",
            example = "보령시")
    @Size(min = 2)
    private String region;
}
