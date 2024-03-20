package calculators;

import com.graphhopper.GHRequest;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.routing.util.VehicleEncodedValuesFactory;
import entities.RouteCalculationRequest;
import entities.RouteCalculationResult;
import utils.Conversions;
import utils.PathFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PathCalculator
{
    private final GraphHopper graphHopper;

    public PathCalculator()
    {
        // DO NOT TOUCH THESE VALUES
        // If these values are changed delete "PathFile.GraphResourceFolder" content in order to reinitialize GraphHopper configuration
        graphHopper = new GraphHopper();
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile(VehicleEncodedValuesFactory.FOOT));
        profiles.add(new Profile(VehicleEncodedValuesFactory.BIKE));
        profiles.add(new Profile(VehicleEncodedValuesFactory.CAR));

        graphHopper.setOSMFile(PathFiles.GraphFile);
        graphHopper.setProfiles(profiles);
        graphHopper.setGraphHopperLocation(PathFiles.GraphResourceFolder);
        graphHopper.importOrLoad();
    }

    public RouteCalculationResult calculateRoute(RouteCalculationRequest calculationRequest)
    {
        var fromLatitude = calculationRequest.departure().getLatitude();
        var fromLongitude = calculationRequest.departure().getLongitude();

        var toLatitude = calculationRequest.arrival().getLatitude();
        var toLongitude = calculationRequest.arrival().getLongitude();

        var profileName = Conversions.toProfile(calculationRequest.transportType());
        var request = new GHRequest(fromLatitude, fromLongitude, toLatitude, toLongitude)
                .setProfile(profileName)
                .setLocale(Locale.UK);
        var response = graphHopper.route(request);
        var path = response.getBest();

        var distance = path.getDistance() / 1000; // TEMPORARY
        var time = (distance * 1000) / calculationRequest.transportType().getSpeedInMetersPerSecond(); // TEMPORARY

        return new RouteCalculationResult(distance, time);
    }
}