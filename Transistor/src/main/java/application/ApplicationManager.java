package application;

import calculators.PathCalculator;
import calculators.RouteCalculator;
import entities.Route;
import entities.RouteCalculationRequest;
import entities.RouteRequest;
import resolvers.Exceptions.CallNotPossibleException;
import resolvers.LocationResolver;
import entities.Coordinate;

public class ApplicationManager
{
    private final LocationResolver locationResolver;
    private final RouteCalculator routeCalculator;

    public ApplicationManager(LocationResolver locationResolver, RouteCalculator routeCalculator)
    {
        this.locationResolver = locationResolver;
        this.routeCalculator = routeCalculator;
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

            Thread.sleep(10 * 1000);

            distance = routeCalculator.Distance2P(departureCoordinates, arrivalCoordinates);
            time = routeCalculator.calculateTime(request.transportType(), distance);

            var pathCalculator = new PathCalculator();
            var calculationResult = pathCalculator.calculateRoute(new RouteCalculationRequest(departureCoordinates, arrivalCoordinates, request.transportType()));

            distance = calculationResult.distanceInKM();
            time = calculationResult.timeInMinutes();
        }
        catch (CallNotPossibleException | InterruptedException e)
        {
            message = e.getMessage();
        }

        return new Route(departureCoordinates, arrivalCoordinates, distance, time, request.transportType(), message);
    }
}