package org.example.safedrivingawareness.service;

import java.util.Arrays;

import org.example.safedrivingawareness.model.PenaltyCategory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service used to perform operations related to penalties
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltiesService {

    /**
     * Find the penalty category for the given speed increase over the legal speed limits.
     * 
     * @param extraSpeed the extra speed over the limits
     * @return a penalty category
     * @see PenaltyCategory
     */
    public PenaltyCategory findPenaltyCategory(int extraSpeed) {
        return Arrays.stream(PenaltyCategory.values())
                .filter(penaltyCategory -> extraSpeed >= penaltyCategory.getLowLimit()
                        && extraSpeed <= penaltyCategory.getHighLimit())
                .findFirst()
                .orElseThrow(() -> {
                    String errorMsg = "The extra speed isn't part of any penalty category";
                    log.error(errorMsg);
                    throw new IllegalArgumentException("The increased speed isn't part of any penalty category");
                });
    }
}
