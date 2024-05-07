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

        String message = "";
        Coordinate departureCoordinates = null;
        Coordinate arrivalCoordinates = null;
        RouteCalculationResult result = null;

        try
        {
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            // TODO: FIX AND RETURN ERROR IF NO COORDINATES FOUND

            double radius1 = 100;
            double radius2 = 100;

            List<Integer> stopIDDeparture  = new ArrayList<>();
            List<Integer> stopIDArrival = new ArrayList<>();

            while (stopIDDeparture.isEmpty())
            {
                stopIDDeparture = DatabaseManager.getInstance().getStopId(departureCoordinates, radius1);
                radius1 += 100;
            }
            while (stopIDArrival.isEmpty())
            {
                stopIDArrival = DatabaseManager.getInstance().getStopId(departureCoordinates, radius2);
                radius1 += 100;
            }

            IRouteCalculator calculator = routeCalculators
                    .stream()
                    .filter(routeCalculator -> routeCalculator.getRouteType() == request.routeType())
                    .findFirst()
                    .orElseThrow();
            result = calculator.calculateRoute(new RouteCalculationRequest(departureCoordinates, arrivalCoordinates, request.transportType()));
        }
        catch (CallNotPossibleException e)
        {
            message = e.getMessage();
        }

        return new Route(departureCoordinates, arrivalCoordinates, result, request.transportType(), message);
    }
}