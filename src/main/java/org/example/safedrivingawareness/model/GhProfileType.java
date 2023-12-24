package org.example.safedrivingawareness.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum GhProfileType {

    CUSTOM_CAR_DEFAULT_SPEED,
    CUSTOM_CAR_SPEED_10_INC,
    CUSTOM_CAR_SPEED_20_INC,
    CUSTOM_CAR_SPEED_30_INC,
    CUSTOM_CAR_SPEED_40_INC,
    CUSTOM_CAR_SPEED_50_INC;

    public static GhProfileType fromSpeedPc(Integer speedIncreaseValue) {
        return switch (speedIncreaseValue) {
            case 0 -> CUSTOM_CAR_DEFAULT_SPEED;
            case 10 -> CUSTOM_CAR_SPEED_10_INC;
            case 20 -> CUSTOM_CAR_SPEED_20_INC;
            case 30 -> CUSTOM_CAR_SPEED_30_INC;
            case 40 -> CUSTOM_CAR_SPEED_40_INC;
            case 50 -> CUSTOM_CAR_SPEED_50_INC;
            default -> {
                String errorMessage = String.format("Illegal speed percentage provided: %s", speedIncreaseValue);
                log.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }
        };
    }

    public String getProfileName() {
        return this.name().toLowerCase();
    }
}
