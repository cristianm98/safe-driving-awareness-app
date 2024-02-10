package org.example.safedrivingawareness.model;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Custom profile type used to identify a GraphHopper car profile. 
 * The types are defined with the following format: CUSTOM_CAR_PROFILE_speedIncreaseValue.
 */
@RequiredArgsConstructor
public enum CustomGhProfileType {

    CUSTOM_CAR_PROFILE_0(0),
    CUSTOM_CAR_PROFILE_5(5),
    CUSTOM_CAR_PROFILE_10(10),
    CUSTOM_CAR_PROFILE_15(15),
    CUSTOM_CAR_PROFILE_20(20),
    CUSTOM_CAR_PROFILE_25(25),
    CUSTOM_CAR_PROFILE_30(30),
    CUSTOM_CAR_PROFILE_35(35),
    CUSTOM_CAR_PROFILE_40(40),
    CUSTOM_CAR_PROFILE_45(45),
    CUSTOM_CAR_PROFILE_50(50),
    CUSTOM_CAR_PROFILE_55(55);

    @Getter
    private final int speedIncreaseValue;

    /**
     * Find the profile type for the given extra speed
     * 
     * @param speedIncreaseValue the increased speed
     * @return the profile corresponding to the given value
     */
    public static Optional<CustomGhProfileType> findBySpeedIncreaseValue(int speedIncreaseValue) {
        return Stream.of(CustomGhProfileType.values())
                .filter(profileName -> profileName.speedIncreaseValue == speedIncreaseValue)
                .findFirst();
    }

    public String nameToLowercase() {
        return this.name().toLowerCase();
    }
}
