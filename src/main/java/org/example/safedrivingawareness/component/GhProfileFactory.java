package org.example.safedrivingawareness.component;

import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.routing.ev.RoadClass;
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
    private static final Map<GhProfileType, Integer> SPEED_INCREASE_MAP = new EnumMap<>(GhProfileType.class);
    private static final Map<RoadClass, Integer> DEFAULT_AVERAGE_SPEED_MAP = new EnumMap<>(RoadClass.class);

    @PostConstruct
    private void initialize() {
        log.info("Initializing map with default speed for each road class");
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.MOTORWAY, 100);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.TRUNK, 70);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.PRIMARY, 65);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.SECONDARY, 60);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.TERTIARY, 50);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.UNCLASSIFIED, 30);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.RESIDENTIAL, 30);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.LIVING_STREET, 5);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.SERVICE, 20);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.ROAD, 20);
        DEFAULT_AVERAGE_SPEED_MAP.put(RoadClass.TRACK, 15);

        log.info("Initializing map with speed increase values for each profile type");
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_DEFAULT_SPEED, 0);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_5_INC, 5);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_10_INC, 10);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_15_INC, 15);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_20_INC, 20);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_25_INC, 25);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_30_INC, 30);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_35_INC, 35);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_40_INC, 40);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_45_INC, 45);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_50_INC, 50);
        SPEED_INCREASE_MAP.put(GhProfileType.CUSTOM_CAR_SPEED_55_INC, 55);
    }

    @NotNull
    public Profile createProfile(GhProfileType profileType) {
        CustomModel customModel = new CustomModel();
        int speedIncreaseValue = SPEED_INCREASE_MAP.get(profileType);
        DEFAULT_AVERAGE_SPEED_MAP.forEach((key, value) -> {
            int defaultAverageSpeed = value;
            float multiplyValue = 1 + speedIncreaseValue * 1.0f / defaultAverageSpeed;
            String condition = "road_class == " + key.name();
            customModel.addToSpeed(Statement.If(condition,
                    Statement.Op.MULTIPLY, String.valueOf(multiplyValue)));
        });
        log.debug("Custom model used for profile: {} is: {}", profileType, customModel);
        Profile profile = new Profile(profileType.getProfileName());
        profile.setVehicle(CAR_VEHICLE_TYPE);
        profile.setTurnCosts(false);
        profile.setCustomModel(customModel);
        log.debug("Created custom profile: {}", profile);
        return profile;
    }
}
