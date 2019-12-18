package com.mitrais.SmartClinic.model;

public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_STAFF("Staff"),
    ROLE_DOCTOR("Doctor"),
    ROLE_PATIENT("Patient");

    private final String displayValue;

    private Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
