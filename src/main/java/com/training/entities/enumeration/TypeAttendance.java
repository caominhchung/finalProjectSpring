package com.training.entities.enumeration;

public enum TypeAttendance {
    P("p"),A("a"),L("l"),E("e"),An("an"),Ln("ln"),En("en");

    private String value;

    TypeAttendance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeAttendance getTypeAttendanceByValue(String value) {
        for (TypeAttendance typeAttendance : TypeAttendance.values()) {
            if (typeAttendance.value.equals(value)) {
                return typeAttendance;
            }
        }
        return null;
    }
}
