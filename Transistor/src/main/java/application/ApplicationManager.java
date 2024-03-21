package application;

import calculators.AerialCalculator;
import calculators.ICalculator;
import entities.*;
import resolvers.Exceptions.CallNotPossibleException;
import resolvers.LocationResolver;

public class ApplicationManager
{
    private final LocationResolver locationResolver;
    private final RequestValidator requestValidator;

    public ApplicationManager(LocationResolver locationResolver, RequestValidator requestValidator)
    {
        this.locationResolver = locationResolver;
        this.requestValidator = requestValidator;
    }

    public Route calculateRouteRequest(RouteRequest request)
    {
        if (!requestValidator.isValidRequest(request))
            new Route(null, null, null, request.transportType(), "Invalid Input");

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