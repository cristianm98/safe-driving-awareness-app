package org.example.safedrivingawareness.service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.util.CustomModel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.component.GhProfileFactory;
import org.example.safedrivingawareness.dto.GraphHopperRouteResult;
import org.example.safedrivingawareness.model.Coordinate;
import org.example.safedrivingawareness.model.GhProfileType;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GraphHopperRouteService {

    // TODO take this from properties
    private static final String ROMANIA_PBF_LATEST_PATH = "/home/cmiholca/IdeaProjects/valhalla-service/custom_files/romania-latest.osm.pbf";

    private final GhProfileFactory ghProfileFactory;
    private GraphHopper graphHopper;

    @PostConstruct
    private void initialize() {
        Profile[] profiles = Arrays.stream(GhProfileType.values())
                .map(ghProfileFactory::getProfile)
                .toArray(Profile[]::new);
        graphHopper = new GraphHopper();
        graphHopper.setOSMFile(ROMANIA_PBF_LATEST_PATH);
        graphHopper.setGraphHopperLocation("target/routing-graph-cache");
        graphHopper.setProfiles(profiles);
        graphHopper.importOrLoad();
    }

    public GraphHopperRouteResult calculateRoute(Coordinate start, Coordinate end, Integer speedPercentageIncrease) {
        log.info("Calculating route between: {} and {} coordinates with speed increased by: {} %", start, end, speedPercentageIncrease);
        GHRequest req = buildCustomCarGhRequest(start, end, GhProfileType.fromSpeedPc(speedPercentageIncrease));
        GHResponse rsp = graphHopper.route(req);
        ResponsePath path = rsp.getBest();
        log.info("Distance between start coordinate: {} and end coordinate: {} is: {}", start, end, path.getDistance());
        List<GraphHopperRouteResult.Path> paths = rsp.getAll().stream()
                .map(responsePath -> {
                    List<List<Double>> coordinates = new ArrayList<>();
                    responsePath.getPoints().forEach(point ->
                            coordinates.add(List.of(point.getLat(), point.getLon()))
                    );
                    GraphHopperRouteResult.PointList points = GraphHopperRouteResult.PointList.builder()
                            .type("LineString")
                            .coordinates(coordinates)
                            .build();
                    return GraphHopperRouteResult.Path.builder()
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
        return new GraphHopperRouteResult(paths);
    }

    private static GHRequest buildCustomCarGhRequest(Coordinate start, Coordinate end, GhProfileType profile) {
        return new GHRequest(start.latitude(), start.longitude(),
                end.latitude(), end.longitude())
                .setProfile(profile.getProfileName())
                .setLocale(Locale.US);
    }
}
