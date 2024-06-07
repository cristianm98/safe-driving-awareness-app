package org.example.safedrivingawareness.component;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import org.example.safedrivingawareness.model.CustomGhProfileType;
import org.springframework.stereotype.Component;

import com.graphhopper.config.Profile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class holding reusable GraphHopper profiles. The profiles are initialized using the {@link GhProfileFactory}
 * and added to a static map and linked to a {@link CustomGhProfileType}.
 * @see GhProfileFactory
 * @see Profile
 * @see CustomGhProfileType
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GraphhopperProfileObjectPool {

    private static final Map<CustomGhProfileType, Profile> GH_PROFILE_MAP = new EnumMap<>(CustomGhProfileType.class);
    private final GhProfileFactory profileFactory;

    @PostConstruct
    private void initialize() {
        log.info("Initializing map containing predefined GraphHopper profiles");
        Stream.of(CustomGhProfileType.values()).forEach(profile -> {
            Profile graphHopperProfile = profileFactory.createProfile(profile);
            GH_PROFILE_MAP.put(profile, graphHopperProfile);
        });
    }

    /**
     * Get the GraphHopper profile corresponding to the given profile type.
     * 
     * @param profileType the profile type
     * @return the corresponding GraphHopper profile
     */
    public Profile getProfile(CustomGhProfileType profileType) {
        return GH_PROFILE_MAP.get(profileType);
    }
}
