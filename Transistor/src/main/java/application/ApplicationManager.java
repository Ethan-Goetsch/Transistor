package application;
import calculators.IRouteCalculator;
import entities.*;
import resolvers.Exceptions.*;
import resolvers.LocationResolver;
import resolvers.Exceptions.NetworkErrorException;

import calculators.AerialCalculator;
import calculators.IRouteCalculator;
import calculators.TransitCalculator;
import database.DatabaseManager;
import database.queries.GetClosetStops;
import entities.*;
import entities.transit.TransitStop;
import resolvers.Exceptions.CallNotPossibleException;
import resolvers.LocationResolver;
//import sun.nio.fs.UnixException;

import java.util.List;

public class ApplicationManager {
    private final LocationResolver locationResolver;
    private final RequestValidator requestValidator;
    private final List<IRouteCalculator> routeCalculators;

    public ApplicationManager(LocationResolver locationResolver, RequestValidator requestValidator, List<IRouteCalculator> routeCalculators) {
        this.locationResolver = locationResolver;
        this.requestValidator = requestValidator;
        this.routeCalculators = routeCalculators;
    }

    public Route calculateRouteRequest(RouteRequest request) {
        if (!requestValidator.isValidRequest(request)) {
            return new Route(null, null, null, request.transportType(), "Invalid Input");
        }
    public Route calculateRoute(RouteRequest request)
    {
        if (!requestValidator.isValidRequest(request))
            return new Route(null, null, null, "Invalid Input");

        String message = "";
        Coordinate departureCoordinates = null;
        Coordinate arrivalCoordinates = null;
        List<Trip> trips = null;

        try {
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            IRouteCalculator calculator = routeCalculators
                    .stream()
                    .filter(routeCalculator -> routeCalculator.getRouteType() == request.routeType())
                    .findFirst()
                    .orElseThrow();
            result = calculator.calculateRoute(new RouteCalculationRequest(departureCoordinates, arrivalCoordinates, request.transportType()));
        } catch (CallNotPossibleException e) {
        try
        {
            // TODO: FIX AND RETURN ERROR IF NO COORDINATES FOUND
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            var originStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(departureCoordinates, 10));
            var destinationStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(arrivalCoordinates, 10));
            var routeResult = getRouteCalculationResult(request,departureCoordinates, arrivalCoordinates, originStops, destinationStops);

            if(routeResult == null || routeResult.isEmpty())
            {
                throw new RouteNotFoundException("No route found");
            }
        }
        catch (RouteNotFoundException | CallNotPossibleException e)
        {
            message = e.getMessage();
        } catch (PostcodeNotFoundException e) {
            message = "Postcode does not exist: " + e.getMessage();
        } catch (InvalidCoordinateException e) {
            message = "Invalid coordinates found: " + e.getMessage();
        } catch (NetworkErrorException e) {
            message = "Network error occurred: " + e.getMessage();
        } catch (RateLimitExceededException e) {
            message = "Rate limit exceeded: " + e.getMessage();
        } catch (Exception e) {
            message = "An unexpected error occurred: " + e.getMessage();
        }

        return new Route(departureCoordinates, arrivalCoordinates, trips, message);
    }

    private List<Trip> getRouteCalculationResult(RouteRequest request, Coordinate departureCoordinates, Coordinate arrivalCoordinates, List<TransitStop> originStops, List<TransitStop> destinationStops)
    {
        List<Trip> trips = new ArrayList<>();
        for (var originStop : originStops)
        {
            for (var destinationStop : destinationStops)
            {
                var transitTrip = new TransitCalculator().calculateRoute(originStop.id(), destinationStop.id());
                if (transitTrip == null) continue;

                // Calculate route from starting location to origin bus stop
                var locationToOriginTrip = new AerialCalculator().calculateRoute(new RouteCalculationRequest(departureCoordinates, originStop.coordinate(), request.transportType()));
                trips.add(locationToOriginTrip);

                trips.add(transitTrip);

                // Calculate route from destination bus stop to final destination
                var destinationToFinal = new AerialCalculator().calculateRoute(new RouteCalculationRequest(destinationStop.coordinate(), arrivalCoordinates, request.transportType()));
                trips.add(destinationToFinal);

                return trips;
            }
        }

        return null;
    }
}