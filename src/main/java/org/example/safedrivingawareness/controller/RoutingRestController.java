package org.example.safedrivingawareness.controller;

import lombok.RequiredArgsConstructor;
import org.example.safedrivingawareness.dto.GraphHopperRouteResponse;
import org.example.safedrivingawareness.model.Coordinate;
import org.example.safedrivingawareness.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for performing routing operations
 */
@RestController
@RequiredArgsConstructor
public class RoutingRestController {

    private final RoutingService graphHopperRouteService;

    /**
     * Calculate the route for the given points and <i>speedIncraese</i> value. This method will return a 
     * {@link GraphHopperRouteResponse} object which contains the list of possible routes for the given input.
     * 
     * @param point         list of points 
     * @param speedIncrease selected speed increase value
     * @return the route result
     * @see GraphHopperRouteResponse
     */
    @GetMapping("/route")
    public ResponseEntity<GraphHopperRouteResponse> calculateRoute(@RequestParam List<String> point,
                                                                 @RequestParam int speedIncrease) {
        GraphHopperRouteResponse graphHopperRouteResult = graphHopperRouteService.calculateRoute(
                Coordinate.fromString(point.get(0)),
                Coordinate.fromString(point.get(1)), speedIncrease);
        return ResponseEntity.ok().body(graphHopperRouteResult);
    }
}
