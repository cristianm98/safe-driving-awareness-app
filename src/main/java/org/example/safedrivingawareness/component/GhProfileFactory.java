package org.example.safedrivingawareness.component;

import java.util.EnumMap;
import java.util.Map;

import org.example.safedrivingawareness.model.CustomGhProfileType;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.routing.ev.RoadClass;
import com.graphhopper.util.CustomModel;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/*
 * Factory used for creating custom GraphHopper car profiles
 * @see Profile
 */
@Component
@Slf4j
public class GhProfileFactory {

    private static final String CAR_VEHICLE_TYPE = "car";
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
    }

    /**
     * Create a GraphHopper car profile based on the given custom profile type. From the given profile type
     * the speed increase corresponding to it is retrieved and used to generate the new average speed values 
     * for all the road classes defined in the {@link DEFAULT_AVERAGE_SPEED_MAP}. The new values are added 
     * to a GraphHopper CustomModel, which in turn is set to the returned profile object.
     * 
     * @param profileType profile's type
     * @return a new GraphHopper car profile with updated average speed values
     * @see CustomGhProfileType
     * @see CustomModel
     * @see Profile
     */
    @NotNull
    public Profile createProfile(CustomGhProfileType profileType) {
        CustomModel customModel = new CustomModel();
        int speedIncreaseValue = profileType.getSpeedIncreaseValue();
        DEFAULT_AVERAGE_SPEED_MAP.forEach((key, value) -> {
            int defaultAverageSpeed = value;
            float multiplyValue = 1 + speedIncreaseValue * 1.0f / defaultAverageSpeed;
            String condition = "road_class == " + key.name();
            customModel.addToSpeed(Statement.If(condition,
                    Statement.Op.MULTIPLY, String.valueOf(multiplyValue)));
        });
        log.debug("Custom model used for profile: {} is: {}", profileType, customModel);
        Profile profile = new Profile(profileType.nameToLowercase());
        profile.setVehicle(CAR_VEHICLE_TYPE);
        profile.setTurnCosts(false);
        profile.setCustomModel(customModel);
        log.info("Created custom profile: {}", profile);
        return profile;
    }
}
