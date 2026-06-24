package com.bloodlink.common.model;

public enum UrgencyLevel {
    CRITICAL, URGENT, NORMAL;

    public static UrgencyLevel fromClient(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return switch (value) {
            case "MEDIUM" -> URGENT;
            case "LOW" -> NORMAL;
            default -> UrgencyLevel.valueOf(value);
        };
    }

    public String toClient() {
        return switch (this) {
            case CRITICAL -> "CRITICAL";
            case URGENT -> "MEDIUM";
            case NORMAL -> "LOW";
        };
    }
}
