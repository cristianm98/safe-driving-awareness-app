package org.example.safedrivingawareness.component;

import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.util.CustomModel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.model.GhProfileType;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@Slf4j
public class GhProfileFactory {

    private static final String CAR_VEHICLE_TYPE = "car";
    private static final Map<GhProfileType, Integer> SPEED_MULTIPLY_NORMALIZED_MAP = new EnumMap<>(GhProfileType.class);
    private static final Map<GhProfileType, Profile> GH_PROFILE_MAP = new EnumMap<>(GhProfileType.class);

    public Profile getProfile(GhProfileType profileType) {
        return GH_PROFILE_MAP.get(profileType);
    }

    @PostConstruct
    private void initialize() {
        log.info("Initializing map with speed multipliers for custom GraphHopper profiles");
        SPEED_MULTIPLY_NORMALIZED_MAP.put(GhProfileType.CUSTOM_CAR_DEFAULT_SPEED, 100);
        SPEED_MULTIPLY_NORMALIZED_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_10_PC_INC, 110);
        SPEED_MULTIPLY_NORMALIZED_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_20_PC_INC, 120);
        SPEED_MULTIPLY_NORMALIZED_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_30_PC_INC, 130);
        SPEED_MULTIPLY_NORMALIZED_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_40_PC_INC, 140);
        SPEED_MULTIPLY_NORMALIZED_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_50_PC_INC, 150);

        log.info("Initializing map containing predefined GraphHopper profiles");
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_DEFAULT_SPEED, createProfile(GhProfileType.CUSTOM_CAR_DEFAULT_SPEED));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_10_PC_INC, createProfile(GhProfileType.CUSTOM_CAR_SPEED_10_PC_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_20_PC_INC, createProfile(GhProfileType.CUSTOM_CAR_SPEED_20_PC_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_30_PC_INC, createProfile(GhProfileType.CUSTOM_CAR_SPEED_30_PC_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_40_PC_INC, createProfile(GhProfileType.CUSTOM_CAR_SPEED_40_PC_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_50_PC_INC, createProfile(GhProfileType.CUSTOM_CAR_SPEED_50_PC_INC));
    }

    @NotNull
    private Profile createProfile(GhProfileType profileType) {
        float multiplyValue = SPEED_MULTIPLY_NORMALIZED_MAP.get(profileType) / 100.0f;
        log.debug("Multiply value used for profile: {} is: {}", profileType, multiplyValue);
        CustomModel customModel = new CustomModel();
        customModel.addToSpeed(Statement.If("true", Statement.Op.MULTIPLY, String.valueOf(multiplyValue)));
        log.debug("Custom model used for profile: {} is: {}", profileType, customModel);
        Profile profile = new Profile(profileType.getProfileName());
        profile.setVehicle(CAR_VEHICLE_TYPE);
        profile.setTurnCosts(false);
        profile.setCustomModel(customModel);
        log.debug("Created custom profile: {}", profile);
        return profile;
    }

    @NotNull
    private Profile createProfile(Integer speedPercentage) {
        GhProfileType profileType = GhProfileType.fromSpeedPc(speedPercentage);
        return createProfile(profileType);
    }
}
