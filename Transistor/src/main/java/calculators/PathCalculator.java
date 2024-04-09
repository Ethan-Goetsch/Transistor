package calculators;

import com.graphhopper.GHRequest;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.routing.util.VehicleEncodedValuesFactory;
import entities.RouteCalculationRequest;
import entities.RouteCalculationResult;
import entities.RouteType;
import utils.Conversions;
import utils.PathLocations;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PathCalculator implements IRouteCalculator
{
    private final GraphHopper graphHopper;

    public PathCalculator()
    {
        // DO NOT TOUCH THESE VALUES
        // If these values are changed delete "PathFile.GraphResourceFolder" content in order to reinitialize GraphHopper configuration
        graphHopper = new GraphHopper();
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile(VehicleEncodedValuesFactory.FOOT).setVehicle(VehicleEncodedValuesFactory.FOOT));
        profiles.add(new Profile(VehicleEncodedValuesFactory.BIKE).setVehicle(VehicleEncodedValuesFactory.BIKE));
        profiles.add(new Profile(VehicleEncodedValuesFactory.CAR).setVehicle(VehicleEncodedValuesFactory.CAR));

        graphHopper.setOSMFile(PathLocations.GRAPH_FILE);
        graphHopper.setProfiles(profiles);
        graphHopper.setGraphHopperLocation(PathLocations.GRAPH_RESOURCE_FOLDER);
        graphHopper.importOrLoad();
    }

    @Override
    public RouteType getRouteType()
    {
        return RouteType.ACTUAL;
    }

    @Override
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

        var distance =  Conversions.metersToKilometers(path.getDistance());
        var time = Conversions.calculateTime(distance, calculationRequest.transportType());

        return new RouteCalculationResult(path, distance, time);
    }
}