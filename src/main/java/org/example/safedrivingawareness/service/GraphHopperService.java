package org.example.safedrivingawareness.service;

import com.graphhopper.*;
import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.util.CustomModel;
import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.dto.GraphHopperRouteResult;
import org.example.safedrivingawareness.model.Coordinate;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class GraphHopperService {

    private static final String ROMANIA_PBF_LATEST_PATH = "/home/cmiholca/IdeaProjects/valhalla-service/custom_files/romania-latest.osm.pbf";
    private static final String CUSTOM_CAR_PROFILE_NAME = "custom_car";
    private static final String CAR_VEHICLE_TYPE = "car";

    public GraphHopperRouteResult calculateRoute(Coordinate start, Coordinate end, Integer speedPercentageIncrease) {
        log.info("Calculating route between: {} and {} coordinates with speed increased by: {} %", start, end, speedPercentageIncrease);
        float speedMultiplier = (float) speedPercentageIncrease / 100;
        Profile profile = createCustomCarProfile(String.valueOf(1 + speedMultiplier));
        GraphHopper graphHopper = initGraphHopper(profile);
        GHRequest req = buildCustomCarGhRequest(start, end);
        GHResponse rsp = graphHopper.route(req);
        ResponsePath path = rsp.getBest();
        log.info("Distance between start coordinate: {} and end coordinate: {} is: {}", start, end, path.getDistance());
        long timeInMs = path.getTime();

        return new GraphHopperRouteResult(timeInMs);
    }

    private static GHRequest buildCustomCarGhRequest(Coordinate start, Coordinate end) {
        return new GHRequest(start.latitude(), start.longitude(),
                end.latitude(), end.longitude())
                .setProfile(CUSTOM_CAR_PROFILE_NAME)
                .setLocale(Locale.US);
    }

    @NotNull
    private static GraphHopper initGraphHopper(Profile profile) {
        GraphHopper graphHopper = new GraphHopper();
        graphHopper.setOSMFile(ROMANIA_PBF_LATEST_PATH);
        graphHopper.setGraphHopperLocation("target/routing-graph-cache");
        graphHopper.setProfiles(profile);
        graphHopper.importOrLoad();
        return graphHopper;
    }

    @NotNull
    private static Profile createCustomCarProfile(String speedMultiplier) {
        CustomModel customModel = createCustomModel(speedMultiplier);
        Profile profile = new Profile(CUSTOM_CAR_PROFILE_NAME).setVehicle(CAR_VEHICLE_TYPE).setTurnCosts(false);
        profile.setCustomModel(customModel);
        return profile;
    }

    @NotNull
    private static CustomModel createCustomModel(String speedMultiplier) {
        CustomModel customModel = new CustomModel();
        customModel.addToSpeed(Statement.If("true", Statement.Op.MULTIPLY, speedMultiplier));
        return customModel;
    }
}
