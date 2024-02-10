package org.example.safedrivingawareness.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO representing the result returned by the routing controller. The structure of the DTO is the same as 
 * the one from the GraphHopepr Routing API:
 * @see <a href="https://docs.graphhopper.com/#operation/getRoute">GraphHopper Routing API</a>
 */
public record GraphHopperRouteResponse(List<Path> paths) {

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
