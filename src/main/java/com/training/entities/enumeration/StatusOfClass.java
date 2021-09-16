package com.training.entities.enumeration;

public enum StatusOfClass {
    Waiting("Waiting"), Running("Running"), Done("Done");

    private String value;

    public String getValue() {
        return value;
    }

    StatusOfClass(String value) {
        this.value = value;
    }

    public static StatusOfClass getStatusByValue(String value) {
        for (StatusOfClass statusOfClass : StatusOfClass.values()) {
            if (statusOfClass.value.equals(value)) {
                return statusOfClass;
            }
        }
        return null;
    }
}
