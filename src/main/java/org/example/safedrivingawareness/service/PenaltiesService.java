package org.example.safedrivingawareness.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.model.PenaltyCategory;
import org.example.safedrivingawareness.model.RoadType;
import org.example.safedrivingawareness.properties.PenaltyMessagesProperties;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltiesService {

    private final PenaltyMessagesProperties penaltyMessagesProperties;
    private final SpeedLimitService speedLimitService;

    public Map<RoadType, PenaltyCategory> getPenalties(int speedIncreasePercentage) {
        Map<RoadType, Integer> defaultSpeedMap = speedLimitService.getDefaultSpeedLimits();
        Map<RoadType, Integer> increasedSpeedMap = speedLimitService.calculateSpeedLimits(speedIncreasePercentage);
        Map<RoadType, PenaltyCategory> result = new EnumMap<>(RoadType.class);
        increasedSpeedMap.forEach(((roadType, increasedSpeedValue) -> {
            int extraSpeed = increasedSpeedValue - defaultSpeedMap.get(roadType);
            result.put(roadType, findPenaltyCategory(extraSpeed));
        }));
        return result;
    }

    public Map<PenaltyCategory, String> getPenaltyMessages() {
        return penaltyMessagesProperties.getPenalties();
    }

    private PenaltyCategory findPenaltyCategory(int extraSpeed) {
        return Arrays.stream(PenaltyCategory.values())
                .filter(penaltyCategory -> extraSpeed >= penaltyCategory.getLowLimit()
                        && extraSpeed <= penaltyCategory.getHighLimit())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The increased speed isn't part of any penalty category"));
    }
}
