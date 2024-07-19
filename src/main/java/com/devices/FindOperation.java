package com.devices;

public enum FindOperation {
    ID("id"),
    BRAND("brand"),
    EMPTY("");

    private final String operator;

    FindOperation(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
