package org.example.safedrivingawareness;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.Profile;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class SafeDrivingAwarenessWebAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SafeDrivingAwarenessWebAppApplication.class, args);
//		FlagEncoder carEncoder = new CarFlagEncoder();
//		EncodingManager encodingManager = new EncodingManager.Builder().add(carEncoder).build();
//		GraphHopper graphHopper = new GraphHopper()
//				.setEncodingManager(encodingManager)
//				.setOSMFile("/home/cmiholca/IdeaProjects/valhalla-service/custom_files/romania-latest.osm.pbf")
//				.setGraphHopperLocation("target/routing-graph-cache")
//				.setProfiles(new Profile("car").setVehicle("car").setTurnCosts(false))
//				.importOrLoad();
//		routing(graphHopper);
//		graphHopper.close();
	}

	public static void routing(GraphHopper hopper) {
		// simple configuration of the request object
		GHRequest req = new GHRequest(47.6565584, 23.5719843, 46.769379, 23.5899542).
				// note that we have to specify which profile we are using even when there is only one like here
						setProfile("car").
				// define the language for the turn instructions
						setLocale(Locale.US);
		GHResponse rsp = hopper.route(req);

		// handle errors
		if (rsp.hasErrors())
			throw new RuntimeException(rsp.getErrors().toString());

		rsp.getAll();

		// use the best path, see the GHResponse class for more possibilities.
		ResponsePath path = rsp.getBest();

		// points, distance in meters and time in millis of the full path
		PointList pointList = path.getPoints();
		double distance = path.getDistance();
		long timeInMs = path.getTime();

		Translation tr = hopper.getTranslationMap().getWithFallBack(Locale.UK);
		InstructionList il = path.getInstructions();
		// iterate over all turn instructions
		for (Instruction instruction : il) {
			 System.out.println("distance " + instruction.getDistance() + " for instruction: " + instruction.getTurnDescription(tr));
		}
		assert il.size() == 6;
		assert Helper.round(path.getDistance(), -2) == 900;
	}
}
