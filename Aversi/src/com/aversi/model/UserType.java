package com.aversi.model;

public enum UserType {
    USER(0), PHARMACIST(1);

    private int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
