package org.example.safedrivingawareness.service;

import java.util.EnumMap;
import java.util.Map;

import org.example.safedrivingawareness.model.RoadType;
import org.springframework.stereotype.Service;

/**
 * Service used to calculate speed limits
 */
@Service
public class SpeedLimitService {

    /**
     * Calculate new speed limits for each {@link RoadType} based on the given speed increase over the legal limits.
     * 
     * @param speedIncreaseValue the extra speed over the legal limits
     * @return a map containing the updated speed limits for each road type
     * @see RoadType
     */
    public Map<RoadType, Integer> calculateSpeedLimits(int speedIncreaseValue) {
        Map<RoadType, Integer> speedLimits = new EnumMap<>(RoadType.class);
        for (var roadType : RoadType.values()) {
            int updatedSpeedLimit = calculateSpeedLimit(roadType, speedIncreaseValue);
            speedLimits.put(roadType, updatedSpeedLimit);
        }
        return speedLimits;
    }

    private int calculateSpeedLimit(RoadType roadType, int speedIncreaseValue) {
        int originalSpeed = roadType.getSpeedLimit();
        return originalSpeed + speedIncreaseValue;
    }
}
