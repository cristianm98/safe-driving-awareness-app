package org.example.safedrivingawareness.service;

import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.model.RoadType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
@Slf4j
public class SpeedLimitService {

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
