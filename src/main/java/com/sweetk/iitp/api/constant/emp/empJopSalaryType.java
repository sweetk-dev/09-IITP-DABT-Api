package com.sweetk.iitp.api.constant.emp;

import com.fasterxml.jackson.annotation.JsonValue;

public enum empJopSalaryType {
    HOURLY("hourly", "시급"),
    MONTHLY("monthly", "월급");

    private final String type;
    private final String name;

    empJopSalaryType(String type, String name) {
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
