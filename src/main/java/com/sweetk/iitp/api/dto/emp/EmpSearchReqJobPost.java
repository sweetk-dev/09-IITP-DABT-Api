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
@Schema(description = "구인 정보 검색 요청")
public class EmpSearchReqJobPost {
    @Schema(description = "고용 형태(옵션)",
            example = "part",
            allowableValues = {"part", "regular", "contract"})
    private empJopEmpType empType;


    @Schema(description = "업종(2글자 이상) (옵션)",
            example = "세탁원")
    @Size(min = 2)
    private String jobType;


    @Schema(description = "사업장 주소(2글자 이상) (옵션)",
            example = "보령시")
    @Size(min = 2)
    private String address;


    @Schema(description = "사업장명(2글자 이상) (옵션)",
            example = "대성산업")
    @Size(min = 2)
    private String name;
}
