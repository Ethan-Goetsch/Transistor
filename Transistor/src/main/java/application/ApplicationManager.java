package application;

import calculators.AerialCalculator;
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

    public Route calculateRoute(RouteRequest request)
    {
        if (!requestValidator.isValidRequest(request))
            return new Route(null, null, null, request.transportType(), "Invalid Input");

        String message = "";
        Coordinate departureCoordinates = null;
        Coordinate arrivalCoordinates = null;
        RouteCalculationResult result = null;

        try
        {
            // TODO: FIX AND RETURN ERROR IF NO COORDINATES FOUND
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            var originStops = DatabaseManager.getInstance().getStop(departureCoordinates, 10);
            var destinationStops = DatabaseManager.getInstance().getStop(arrivalCoordinates, 10);

            result = getRouteCalculationResult(request, departureCoordinates, arrivalCoordinates, result, originStops, destinationStops);
        }
        catch (CallNotPossibleException e)
        {
            message = e.getMessage();
        }

        return new Route(departureCoordinates, arrivalCoordinates, result, request.transportType(), message);
    }

    private RouteCalculationResult getRouteCalculationResult(RouteRequest request, Coordinate departureCoordinates, Coordinate arrivalCoordinates, RouteCalculationResult result, List<TransitStop> originStops, List<TransitStop> destinationStops)
    {
        for (TransitStop originStop : originStops)
        {
            for (TransitStop destinationStop : destinationStops)
            {
                var calculation = new TransitCalculator().calculateRoute(originStop, destinationStop);
                if (calculation == null) continue;

                // Calculate route from starting location to origin bus stop
                var locationToOriginResult = new AerialCalculator().calculateRoute(new RouteCalculationRequest(departureCoordinates, originStop.coordinate(), request.transportType()));

                // Calculate route from destination bus stop to final destination
                var destinationToFinal = new AerialCalculator().calculateRoute(new RouteCalculationRequest(destinationStop.coordinate(), arrivalCoordinates, request.transportType()));

                return calculation
                        .merge(locationToOriginResult, false)
                        .merge(destinationToFinal, true);
            }
        }

        return null;
    }
}