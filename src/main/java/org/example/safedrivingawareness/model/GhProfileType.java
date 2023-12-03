package org.example.safedrivingawareness.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum GhProfileType {

    CUSTOM_CAR_SPEED_10_PC_INC,
    CUSTOM_CAR_SPEED_20_PC_INC,
    CUSTOM_CAR_SPEED_30_PC_INC,
    CUSTOM_CAR_SPEED_40_PC_INC,
    CUSTOM_CAR_SPEED_50_PC_INC;

    public static GhProfileType fromSpeedPc(Integer speedPercentage) {
        return switch (speedPercentage) {
            case 10 -> CUSTOM_CAR_SPEED_10_PC_INC;
            case 20 -> CUSTOM_CAR_SPEED_20_PC_INC;
            case 30 -> CUSTOM_CAR_SPEED_30_PC_INC;
            case 40 -> CUSTOM_CAR_SPEED_40_PC_INC;
            case 50 -> CUSTOM_CAR_SPEED_50_PC_INC;
            default -> {
                String errorMessage = String.format("Illegal speed percentage provided: %s", speedPercentage);
                log.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }
        };
    }

    public String getProfileName() {
        return this.name().toLowerCase();
    }
}
