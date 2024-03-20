package application;

import calculators.RouteCalculator;
import entities.Route;
import entities.RouteRequest;
import resolvers.LocationResolver;

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
        var departureCoordinates = locationResolver.getCordsFromPostCode(request.destination());
        var arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

        var distance = routeCalculator.Distance2P(departureCoordinates.getLatitude(), departureCoordinates.getLongitude(), arrivalCoordinates.getLatitude(), arrivalCoordinates.getLongitude());
        var time = routeCalculator.calculateTime(request.transportType(), distance);

        return new Route(departureCoordinates, arrivalCoordinates, distance, time);
    }
}