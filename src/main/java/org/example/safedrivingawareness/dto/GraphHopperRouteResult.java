package org.example.safedrivingawareness.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public record GraphHopperRouteResult(List<Path> paths) {

    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Path{
        private double distance;
        private double weight;
        private long time;
        private PointList points;
        private List<Instruction> instructions;
        private double ascend;
        private double descend;
        @JsonProperty("snapped_waypoints")
        private PointList snappedWaypoints;
    }

    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PointList {
        private String type;
        private List<List<Double>> coordinates;
    }

    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Instruction {
        private double distance;
        private double heading;
        private int sign;
        private List<Integer> interval;
        private String text;
        private long time;
        @JsonProperty("street_name")
        private String streetName;
    }
}
