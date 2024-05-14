package application;

import calculators.IRouteCalculator;
import database.DatabaseManager;
import entities.*;
import resolvers.Exceptions.CallNotPossibleException;
import resolvers.LocationResolver;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager
{
    private final LocationResolver locationResolver;
    private final RequestValidator requestValidator;
    private final List<IRouteCalculator> routeCalculators;


    public ApplicationManager(LocationResolver locationResolver, RequestValidator requestValidator, List<IRouteCalculator> routeCalculators)
    {
        this.locationResolver = locationResolver;
        this.requestValidator = requestValidator;
        this.routeCalculators = routeCalculators;
    }

    public Route calculateRouteRequest(RouteRequest request)
    {
        if (!requestValidator.isValidRequest(request))
            return new Route(null, null, null, request.transportType(), "Invalid Input");

        DatabaseManager database = DatabaseManager.getInstance();
        String message = "";
        Coordinate departureCoordinates = null;
        Coordinate arrivalCoordinates = null;
        RouteCalculationResult locationToOriginResult = null;

        try
        {
            IRouteCalculator calculator = routeCalculators
                    .stream()
                    .filter(routeCalculator -> routeCalculator.getRouteType() == request.routeType())
                    .findFirst()
                    .orElse(routeCalculators.getFirst());

            // TODO: FIX AND RETURN ERROR IF NO COORDINATES FOUND
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            var originStop = //;
            List<Integer> stopIdOrigin = database.getStopId(departureCoordinates);
            List<Integer>  stopIdDestination = database.getStopId(arrivalCoordinates);

            /*
            Steps:
                1. Get origin stop id
                2. Get destination stop id
                3. Get route from starting location to origin stop id
                4. Get trip from origin to destination
                5. Get shapes of trip
                6. Return path
             */

            // TODO: GET LATITUDE AND LONGITUDE OF ORIGIN STOP ID
            locationToOriginResult = calculator.calculateRoute(new RouteCalculationRequest(departureCoordinates, originStop, request.transportType()));
            List<Integer> tripsFromOriginToDestination = database.GetTrip(originStopId, destinationStopId);
            List<Integer> pathsFromOriginToDestination = database.GetPath(tripId);
        }
        catch (CallNotPossibleException e)
        {
            message = e.getMessage();
        }

        return new Route(departureCoordinates, arrivalCoordinates, locationToOriginResult, request.transportType(), message);
    }
}