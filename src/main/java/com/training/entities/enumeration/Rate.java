package com.training.entities.enumeration;

public enum Rate {
    AA("A+"),A("A"),B("B"),C("C"),D("D");

    private String value;

    Rate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Rate getRateByValue(String value) {
        for (Rate rate : Rate.values()) {
            if (rate.value.equals(value)) {
                return rate;
            }
        }
        return null;
    }
}
