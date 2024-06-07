package org.example.safedrivingawareness.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PenaltyCategory {

    CATEGORY_0(0, 9),
    CATEGORY_1(10, 20),
    CATEGORY_2(21, 30),
    CATEGORY_3(31, 40),
    CATEGORY_4(41, 50),
    CATEGORY_5(51, Integer.MAX_VALUE);

    private final int lowLimit;
    private final int highLimit;
}
