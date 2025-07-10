package com.sweetk.iitp.api.constant.emp;

import com.fasterxml.jackson.annotation.JsonValue;

public enum empJopEmpType {
    PART_TIME("part", "시간제"),
    REGULAR("regular", "상용직"),
    CONTRACT("contract", "계약직");

    private final String type;
    private final String name;

    empJopEmpType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return type;
    }
}
