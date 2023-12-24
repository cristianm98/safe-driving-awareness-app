package org.example.safedrivingawareness.service;

import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.model.RoadType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
@Slf4j
public class SpeedLimitService {

    public Map<RoadType, Integer> calculateSpeedLimits(int speedIncreasePercentage) {
        Map<RoadType, Integer> speedLimits = new EnumMap<>(RoadType.class);
        for (var roadType : RoadType.values()) {
            int updatedSpeedLimit = calculateSpeedLimit(roadType, speedIncreasePercentage);
            speedLimits.put(roadType, updatedSpeedLimit);
        }
        return speedLimits;
    }

    public Map<RoadType, Integer> getDefaultSpeedLimits() {
        Map<RoadType, Integer> speedLimits = new EnumMap<>(RoadType.class);
        for (var roadType : RoadType.values()) {
            speedLimits.put(roadType, roadType.getSpeedLimit());
        }
        return speedLimits;
    }

    private int calculateSpeedLimit(RoadType roadType, int speedIncreasePercentage) {
        int originalSpeed = roadType.getSpeedLimit();
        return originalSpeed + (originalSpeed * speedIncreasePercentage) / 100;
    }
}
