package com.sweetk.iitp.api.constant;

public final class ApiConstants {
    private ApiConstants() {
        throw new IllegalStateException("API Constants class");
    }

    public static final String API_KEY_HEADER = "X-API-KEY";

    public static final class ApiPath {
        private ApiPath() {
            throw new IllegalStateException("ApiConstants- ApiPath constants Error");
        }

        public static final String API_V1 = "/api/v1";

        public static final String API_V1_COMMON = API_V1 + "/comm";
        public static final String API_V1_CLIENT = API_V1 + "/mgmt/client";


        //For Poi
        public static final String API_V1_POI = API_V1 + "/poi";

        //For Basic
        public static final String API_V1_BASIC = API_V1 + "/basic";
        public static final String API_V1_BASIC_HOUSING = API_V1_BASIC + "/housing";
        public static final String API_V1_BASIC_health  = API_V1_BASIC + "/health";
        public static final String API_V1_BASIC_AID = API_V1_BASIC + "/aid";
        public static final String API_V1_BASIC_EDU = API_V1_BASIC + "/edu";
        public static final String API_V1_BASIC_EMP  = API_V1_BASIC + "/emp";
        public static final String API_V1_BASIC_SOCIAL = API_V1_BASIC + "/social";
        public static final String API_V1_BASIC_FACILITY = API_V1_BASIC + "/facility";


        //For Employment
        public static final String API_V1_EMP = API_V1 + "/emp";
    }


    public static final class Param {
        private Param() {
            throw new IllegalStateException("ApiConstants - Param constants Error");
        }

        public static final Integer DEFAULT_PAGE = 1;
        public static final Integer DEFAULT_SIZE = 30;

        public static final Integer MAX_PAGE = 1000;
        public static final Integer MAX_SIZE = 100;

        public static final Integer DEFAULT_STAT_REG_YEAR_PERIOD = 3;   //Default 요청 통계 기간
        public static final Integer MAX_STAT_REQ_YEAR_PERIOD = 10;      // 최대 조회할 수 있는 요청 통계 기간
    }
} 