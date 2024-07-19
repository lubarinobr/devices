package com.devices.enums;

import java.util.Arrays;

public enum FindOperation {
    ID("id"),
    BRAND("brand"),
    EMPTY("");

    private final String name;

    FindOperation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
