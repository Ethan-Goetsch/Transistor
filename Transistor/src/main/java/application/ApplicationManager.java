package application;

import calculators.IRouteCalculator;
import calculators.TransitCalculator;
import database.DatabaseManager;
import entities.*;
import entities.transit.TransitStop;
import resolvers.Exceptions.CallNotPossibleException;
import resolvers.LocationResolver;

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

        String message = "";
        Coordinate departureCoordinates = null;
        Coordinate arrivalCoordinates = null;
        RouteCalculationResult result = null;

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

            var stopIdOrigin = DatabaseManager.getInstance().getStop(departureCoordinates);
            var stopIdDestination = DatabaseManager.getInstance().getStop(arrivalCoordinates);

            var originStop = stopIdOrigin.getFirst();
            var destinationStop = stopIdDestination.getFirst();

            // Calculate route from starting location to origin bus stop
            var locationToOriginResult = calculator.calculateRoute(new RouteCalculationRequest(departureCoordinates, originStop.coordinate(), request.transportType()));
            result = new TransitCalculator().calculateRoute(originStop, destinationStop);
            result = result.merge(locationToOriginResult);
        }
        catch (CallNotPossibleException e)
        {
            message = e.getMessage();
        }

        return new Route(departureCoordinates, arrivalCoordinates, result, request.transportType(), message);
    }
}