package application;

import calculators.PathCalculator;
import calculators.AerialCalculator;
import entities.Route;
import entities.RouteCalculationRequest;
import entities.RouteRequest;
import resolvers.Exceptions.CallNotPossibleException;
import resolvers.LocationResolver;
import entities.Coordinate;

public class ApplicationManager
{
    private final LocationResolver locationResolver;
    private final AerialCalculator aerialCalculator;

    public ApplicationManager(LocationResolver locationResolver, AerialCalculator aerialCalculator)
    {
        this.locationResolver = locationResolver;
        this.aerialCalculator = aerialCalculator;
    }

    public Route calculateRouteRequest(RouteRequest request)
    {
        String message = "";
        Coordinate departureCoordinates = null;
        Coordinate arrivalCoordinates = null;
        double distance = 0.0;
        double time = 0.0;

        try
        {
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            distance = aerialCalculator.Distance2P(departureCoordinates, arrivalCoordinates);
            time = aerialCalculator.calculateTime(request.transportType(), distance);

            var pathCalculator = new PathCalculator();
            var calculationResult = pathCalculator.calculateRoute(new RouteCalculationRequest(departureCoordinates, arrivalCoordinates, request.transportType()));

            distance = calculationResult.distanceInKM();
            time = calculationResult.timeInMinutes();
        }
        catch (CallNotPossibleException e)
        {
            System.out.println("setting error message");
            message = e.getMessage();
        }

        return new Route(departureCoordinates, arrivalCoordinates, distance, time, request.transportType(), message);
    }
}