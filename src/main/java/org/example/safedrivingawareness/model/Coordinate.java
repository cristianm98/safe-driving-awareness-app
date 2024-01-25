package org.example.safedrivingawareness.model;

public record Coordinate(float latitude, float longitude) {

    public static Coordinate fromString(String point) {
        String[] pointSplit = point.split(",");
        return new Coordinate(Float.parseFloat(pointSplit[0]), Float.parseFloat(pointSplit[1]));
    }
}
