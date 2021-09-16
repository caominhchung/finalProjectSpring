package com.training.entities.enumeration;



public enum Role{

    ROLE_TRAINEE("ROLE_TRAINEE"), ROLE_ADMIN("ROLE_ADMIN"), ROLE_TRAINER("ROLE_TRAINER"), ROLE_CHANGE_PASSWORD_PRIVILEGE("ROLE_CHANGE_PASSWORD_PRIVILEGE");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role getRoleByValue(String value) {
        for (Role role : Role.values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        return null;
    }
}
