package org.example.safedrivingawareness.component;

import com.graphhopper.config.Profile;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.model.GhProfileType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class GhProfileObjectPool {

    private static final Map<GhProfileType, Profile> GH_PROFILE_MAP = new EnumMap<>(GhProfileType.class);
    private final GhProfileFactory profileFactory;

    @PostConstruct
    private void initialize() {
        log.info("Initializing map containing predefined GraphHopper profiles");
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_DEFAULT_SPEED, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_DEFAULT_SPEED));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_5_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_5_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_10_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_10_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_15_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_15_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_20_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_20_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_25_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_25_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_30_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_30_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_35_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_35_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_40_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_40_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_45_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_45_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_50_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_50_INC));
        GH_PROFILE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_55_INC, profileFactory.createProfile(GhProfileType.CUSTOM_CAR_SPEED_55_INC));
    }

    public Profile getProfile(GhProfileType profileType) {
        return GH_PROFILE_MAP.get(profileType);
    }
}
