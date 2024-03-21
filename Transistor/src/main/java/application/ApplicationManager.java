package application;

import calculators.ICalculator;
import calculators.PathCalculator;
import calculators.AerialCalculator;
import entities.*;
import resolvers.Exceptions.CallNotPossibleException;
import resolvers.LocationResolver;

public class ApplicationManager
{
    private final LocationResolver locationResolver;

    public ApplicationManager(LocationResolver locationResolver)
    {
        this.locationResolver = locationResolver;
    }

    public Route calculateRouteRequest(RouteRequest request)
    {
        String message = "";
        Coordinate departureCoordinates = null;
        Coordinate arrivalCoordinates = null;
        RouteCalculationResult result = null;

        try
        {
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            Thread.sleep(10 * 1000);
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            ICalculator calculator = new AerialCalculator();
            result = calculator.calculateRoute(new RouteCalculationRequest(departureCoordinates, arrivalCoordinates, request.transportType()));

        }
        catch (CallNotPossibleException | InterruptedException e)
        {
            message = e.getMessage();
        }

        return new Route(departureCoordinates, arrivalCoordinates, result, request.transportType(), message);
    }
}