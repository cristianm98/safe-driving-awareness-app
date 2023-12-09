package org.example.safedrivingawareness.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoadType {

    LOCALITY(50, "Locality"),
    MOTORWAY(130, "Motorway"),
    EXPRESSWAY(120, "Expressway"),
    EUROPEAN_ROAD(100, "European road"),
    OTHER(90, "Other road");

    private final int speedLimit;
    private final String displayText;
}
