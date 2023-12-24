package org.example.safedrivingawareness.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.model.PenaltyCategory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltiesService {

    public PenaltyCategory findPenaltyCategory(int extraSpeed) {
        return Arrays.stream(PenaltyCategory.values())
                .filter(penaltyCategory -> extraSpeed >= penaltyCategory.getLowLimit()
                        && extraSpeed <= penaltyCategory.getHighLimit())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The increased speed isn't part of any penalty category"));
    }
}
