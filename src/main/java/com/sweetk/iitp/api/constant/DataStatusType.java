package com.sweetk.iitp.api.constant;


// System Common Code group :: data_status
public enum DataStatusType {
    PENDING ( "P","대기 중", "Pending: 내부 승인·검토 전" ),
    READY ( "R","준비됨", "Ready: 승인 완료, 활성화 조건 대기 중"),
    ACTIVE ( "A","활성", "Active: 운영 사용 중"),
    INACTIVE ( "I", "비활성", "Inactive: 일시 미사용"),
    DELETED ( "D","삭제", "Deleted: 논리 삭제 상태");


    String code;
    String name;
    String description;


    DataStatusType(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }
}
