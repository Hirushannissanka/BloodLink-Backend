package com.bloodlink.common.model;

import java.util.Map;

public enum BloodType {
    A_POS, A_NEG, B_POS, B_NEG, AB_POS, AB_NEG, O_POS, O_NEG;

    private static final Map<String, BloodType> FRONTEND_VALUES = Map.of(
            "A+", A_POS,
            "A-", A_NEG,
            "B+", B_POS,
            "B-", B_NEG,
            "AB+", AB_POS,
            "AB-", AB_NEG,
            "O+", O_POS,
            "O-", O_NEG
    );

    public static BloodType fromClient(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        BloodType frontendValue = FRONTEND_VALUES.get(value);
        return frontendValue != null ? frontendValue : BloodType.valueOf(value);
    }

    public String toClient() {
        return switch (this) {
            case A_POS -> "A+";
            case A_NEG -> "A-";
            case B_POS -> "B+";
            case B_NEG -> "B-";
            case AB_POS -> "AB+";
            case AB_NEG -> "AB-";
            case O_POS -> "O+";
            case O_NEG -> "O-";
        };
    }
}
