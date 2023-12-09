package org.example.safedrivingawareness.controller;

import lombok.RequiredArgsConstructor;
import org.example.safedrivingawareness.dto.GraphHopperRouteResult;
import org.example.safedrivingawareness.model.Coordinate;
import org.example.safedrivingawareness.service.GraphHopperRouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GraphHopperRestController {

    private final GraphHopperRouteService graphHopperRouteService;

    @GetMapping("/route")
    public ResponseEntity<GraphHopperRouteResult> calculateRoute() {
        GraphHopperRouteResult graphHopperRouteResult = graphHopperRouteService.calculateRoute(
                new Coordinate(47.6565584f, 23.5719843f),
                new Coordinate(46.769379f, 23.5899542f), 30);
        return ResponseEntity.ok().body(graphHopperRouteResult);
    }
}
