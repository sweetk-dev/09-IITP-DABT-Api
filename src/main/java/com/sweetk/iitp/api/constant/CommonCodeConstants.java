package com.sweetk.iitp.api.constant;

public class CommonCodeConstants {
    private CommonCodeConstants() {
        throw new IllegalStateException("Common Code  Constants class");
    }

    public static final class SysCodeGroup {
        private SysCodeGroup() {
            throw new IllegalStateException("SysCodeGroup Constants class");
        }

        // 시스템 관리
        public static final String SYS_ADMIN_ROLES = "sys_admin_roles"; // 관리자 역할 코드
        public static final String SYS_DATA_STATUS = "sys_data_status"; // 데이터 상태 코드
        //Sido Code
        public static final String SIDO_CODE = "sido_code"; // 내부용 시도 코드(prefix:9)

        // FAQ 관련
        public static final String FAQ_TYPE = "faq_type"; // FAQ 유형
        public static final String FAQ_STATUS = "faq_status"; // FAQ 상태 (공개/비공개)
        // QNA 관련
        public static final String QNA_TYPE = "qna_type"; // QNA 유형
        public static final String QNA_STATUS = "qna_status"; // QNA 상태 (답변대기/답변완료/비공개)
        // 공지사항 관련
        public static final String NOTICE_TYPE = "notice_type"; // 공지사항 유형
    }

}
