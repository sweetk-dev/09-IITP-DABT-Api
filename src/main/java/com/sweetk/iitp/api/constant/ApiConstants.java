package com.sweetk.iitp.api.constant;

public final class ApiConstants {
    private ApiConstants() {
        throw new IllegalStateException("Constants class");
    }

    public static final class Auth {
        private Auth() {
            throw new IllegalStateException("Auth constants class");
        }

        public static final String AUTH_HEADER = "X-API-KEY";
        public static final String BEARER_PREFIX = "Bearer ";
        public static final String AUTHORIZATION_HEADER = "Authorization";
    }

    public static final class Role {
        private Role() {
            throw new IllegalStateException("Role constants class");
        }

        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_CLIENT = "ROLE_CLIENT";
    }

    public static final class ApiPath {
        private ApiPath() {
            throw new IllegalStateException("ApiPath constants class");
        }

        public static final String API_V1 = "/api/v1";

        public static final String API_V1_POI = API_V1 + "/poi";

        public static final String API_V1_BASIC = API_V1 + "/basic";
        public static final String API_V1_BASIC_HOUSING = API_V1_BASIC + "/housing";
        public static final String API_V1_BASIC_health  = API_V1_BASIC + "/health";
        public static final String API_V1_BASIC_AID = API_V1_BASIC + "/aid";
        public static final String API_V1_BASIC_EDU = API_V1_BASIC + "/edu";
        public static final String API_V1_BASIC_EMP  = API_V1_BASIC + "/emp";
        public static final String API_V1_BASIC_SOCIAL = API_V1_BASIC + "/social";
        public static final String API_V1_BASIC_FACILITY = API_V1_BASIC + "/facility";


        public static final String API_V1_PUBLIC = API_V1 + "/public";
        public static final String API_V1_ADMIN = API_V1 + "/admin";
        public static final String API_V1_USER = API_V1 + "/users";
        public static final String API_V1_AUTH = API_V1 + "/auth";
    }

} 