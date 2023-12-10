package org.example.safedrivingawareness.controller;

import lombok.RequiredArgsConstructor;
import org.example.safedrivingawareness.dto.GraphHopperRouteResult;
import org.example.safedrivingawareness.model.Coordinate;
import org.example.safedrivingawareness.service.GraphHopperRouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GraphHopperRestController {

    private final GraphHopperRouteService graphHopperRouteService;

    @GetMapping("/route")
    public ResponseEntity<GraphHopperRouteResult> calculateRoute(@RequestParam List<String> point,
                                                                 @RequestParam int speedIncrease) {
        GraphHopperRouteResult graphHopperRouteResult = graphHopperRouteService.calculateRoute(
                Coordinate.fromString(point.get(0)),
                Coordinate.fromString(point.get(1)), speedIncrease);
        return ResponseEntity.ok().body(graphHopperRouteResult);
    }
}
