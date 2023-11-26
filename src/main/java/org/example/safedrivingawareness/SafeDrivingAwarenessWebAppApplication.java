package org.example.safedrivingawareness;

import org.example.safedrivingawareness.model.Coordinate;
import org.example.safedrivingawareness.service.GraphHopperService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafeDrivingAwarenessWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafeDrivingAwarenessWebAppApplication.class, args);
//        var service = new GraphHopperService();
//        service.calculateRoute(new Coordinate(47.6565584f, 23.5719843f),
//                new Coordinate(46.769379f, 23.5899542f), 10);
    }
}
