package org.example.safedrivingawareness.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.example.safedrivingawareness.component.GraphhopperProfileObjectPool;
import org.example.safedrivingawareness.dto.GraphHopperRouteResponse;
import org.example.safedrivingawareness.model.Coordinate;
import org.example.safedrivingawareness.model.CustomGhProfileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.util.Parameters;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service used to perform routing operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoutingService {

    @Value("${graphHopper.osmFilePath}")
    private final String osmFilePath;
    @Value("${graphHopper.graphCachePath}")
    private final String graphCachePath;
    private final GraphhopperProfileObjectPool ghProfileObjectPool;
    private GraphHopper graphHopper;

    @PostConstruct
    private void initialize() {
        Profile[] profiles = Arrays.stream(CustomGhProfileType.values())
                .map(ghProfileObjectPool::getProfile)
                .toArray(Profile[]::new);
        graphHopper = new GraphHopper();
        graphHopper.setOSMFile(osmFilePath);
        graphHopper.setGraphHopperLocation(graphCachePath);
        graphHopper.setProfiles(profiles);
        graphHopper.importOrLoad();
    }

    /**
     * Calculate routes betweens specified coordinates based on the given speed increase value. 
     * The routing is performed by following the next steps:
     * 1.Create a custom GraphHopper routing request based on a predefined GraphHopper Profile. The algorithm used
     * for routing is set at this step together with the path details (e.g: "time", "distance") which are of interest.
     * 2.Routing is performed by calling the route method on {@link GraphHopper} object with the previous request passed as parameter.
     * 3.The response received from {@link GraphHopper} object is then mapped to {@link GraphHopperRouteResponse}.
     * 
     * @param start         start coordinates
     * @param end           destination coordinates
     * @param speedIncrease the extra speed applied to calculate the routes
     * @return the {@link GraphHopperRouteResponse} containing the calculated routes
     * @see Profile
     * @see Parameters.Algorithms
     * @see GraphHopper
     * @see GHRequest
     * @see GHResponse
     */
    public GraphHopperRouteResponse calculateRoute(Coordinate start, Coordinate end, Integer speedIncrease) {
        log.info("Calculating route between: {} and {} coordinates with speed increased by: {}", start, end,
                speedIncrease);
        Optional<CustomGhProfileType> customGhProfileOptional = CustomGhProfileType
                .findBySpeedIncreaseValue(speedIncrease); // TODO throw exception if profile type not found
        GHRequest req = buildCustomCarGhRequest(start, end, customGhProfileOptional.get());
        GHResponse rsp = graphHopper.route(req);
        List<GraphHopperRouteResponse.Path> paths = processPaths(rsp);
        return new GraphHopperRouteResponse(paths);
    }

    private List<GraphHopperRouteResponse.Path> processPaths(GHResponse rsp) {
        return rsp.getAll().stream()
                .map(responsePath -> {
                    List<List<Double>> coordinates = new ArrayList<>();
                    responsePath.getPoints().forEach(point -> coordinates.add(List.of(point.getLat(), point.getLon())));
                    GraphHopperRouteResponse.PointList points = GraphHopperRouteResponse.PointList.builder()
                            .type("LineString")
                            .coordinates(coordinates)
                            .build();
                    return GraphHopperRouteResponse.Path.builder()
                            .distance(responsePath.getDistance())
                            .weight(responsePath.getRouteWeight())
                            .time(responsePath.getTime())
                            .points(points)
                            .instructions(Collections.emptyList())
                            .ascend(responsePath.getAscend())
                            .descend(responsePath.getDescend())
                            .build();
                })
                .toList();
    }

    private static GHRequest buildCustomCarGhRequest(Coordinate start, Coordinate end, CustomGhProfileType profile) {
        return new GHRequest(start.latitude(), start.longitude(),
                end.latitude(), end.longitude())
                .setProfile(profile.nameToLowercase())
                .setLocale(Locale.US)
                .setAlgorithm(Parameters.Algorithms.ALT_ROUTE)
                .setPathDetails(List.of("time", "distance", "max_speed", "road_class"));
    }
}
