package application;

import calculators.RouteCalculator;
import entities.Route;
import entities.RouteRequest;
import resolvers.Exceptions.CallNotPossibleException;
import resolvers.LocationResolver;
import utils.Coordinates;

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
        Coordinates departureCoordinates = null;
        Coordinates arrivalCoordinates = null;
        double distance = 0.0;
        double time = 0.0;

        try
        {
            departureCoordinates = locationResolver.getCordsFromPostCode(request.destination());
            try{
                Thread.sleep(10000);// prevents braking
            }catch(InterruptedException ex){}
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());
            distance = routeCalculator.Distance2P(departureCoordinates, arrivalCoordinates);
            time = routeCalculator.calculateTime(request.transportType(), distance);
        }
        catch (CallNotPossibleException e)
        {
            System.out.println("setting error message");
            message = e.getMessage();
        }


        return new Route(departureCoordinates, arrivalCoordinates, distance, time, request.transportType(), message);
    }
}