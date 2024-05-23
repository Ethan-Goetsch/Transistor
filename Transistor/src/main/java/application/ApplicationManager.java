package application;

import calculators.AerialCalculator;
import calculators.IRouteCalculator;
import calculators.TransitCalculator;
import database.DatabaseManager;
import database.queries.GetClosetStops;
import entities.*;
import entities.exceptions.*;
import entities.transit.TransitStop;
import resolvers.LocationResolver;

import java.time.LocalTime;
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

    public Route calculateRouteRequest(RouteRequest request)
    {
        if (!requestValidator.isValidRequest(request))
        {
            return new Route(null, null, null, "Invalid Input");
        }

        String message = "";
        Coordinate departureCoordinates = null;
        Coordinate arrivalCoordinates = null;
        Journey journey = null;

        try
        {
            // TODO: FIX AND RETURN ERROR IF NO COORDINATES FOUND
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            var originStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(departureCoordinates, 10));
            var destinationStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(arrivalCoordinates, 10));
            var routeResult = getRouteCalculationResult(request,departureCoordinates, arrivalCoordinates, originStops, destinationStops);

            if(routeResult == null || routeResult.getTrips().isEmpty())
            {
                throw new RouteNotFoundException("No route found");
            }

            journey = getRouteCalculationResult(request, departureCoordinates, arrivalCoordinates, originStops, destinationStops);
        }
        catch (RouteNotFoundException e)
        {
            message = "Route cannot be found: " + e.getMessage();
        }
        catch (CallNotPossibleException e)
        {
            message = "Call is not possible: " + e.getMessage();
        }
        catch (PostcodeNotFoundException e)
        {
            message = "Postcode does not exist: " + e.getMessage();
        }
        catch (InvalidCoordinateException e)
        {
            message = "Invalid coordinates found: " + e.getMessage();
        }
        catch (NetworkErrorException e)
        {
            message = "Network error occurred: " + e.getMessage();
        }
        catch (RateLimitExceededException e)
        {
            message = "Rate limit exceeded: " + e.getMessage();
        }
        catch (Exception e)
        {
            message = "An unexpected error occurred: " + e.getMessage();
            e.printStackTrace();
        }

        return new Route(departureCoordinates, arrivalCoordinates, journey, message);
    }

    private Journey getRouteCalculationResult(RouteRequest request, Coordinate departureCoordinates, Coordinate arrivalCoordinates, List<TransitStop> originStops, List<TransitStop> destinationStops)
    {
        Journey earliestJourney = null;
        LocalTime earlieastArrival = LocalTime.MAX;

        for (var originStop : originStops)
        {
            for (var destinationStop : destinationStops)
            {
                var journey = new Journey();
                // Calculate the actual bus trip from starting bus stop to the final bus stop
                var transitTrip = new TransitCalculator().calculateRoute(originStop.id(), destinationStop.id());
                if (transitTrip == null) continue;

                // Calculate route from starting location to origin bus stop
                var locationToOriginTrip = new AerialCalculator().calculateRoute(
                        new RouteCalculationRequest(departureCoordinates,
                                originStop.coordinate(),
                                transitTrip.getDepartureTime(),
                                transitTrip.getDepartureTime(),
                                request.transportType()));

                // Calculate route from destination bus stop to final destination
                var destinationToFinal = new AerialCalculator().calculateRoute(
                        new RouteCalculationRequest(destinationStop.coordinate(),
                                arrivalCoordinates,
                                transitTrip.getArrivalTime(),
                                transitTrip.getArrivalTime(),
                                request.transportType()));

                journey.addTrip(locationToOriginTrip);
                journey.addTrip(transitTrip);
                journey.addTrip(destinationToFinal);

                var journeyArrivalTime = journey.getArrivalTime();
                if (journeyArrivalTime.isBefore(earlieastArrival))
                {
                    earliestJourney = journey;
                    earlieastArrival = journeyArrivalTime;
                }
            }
        }

        return earliestJourney;
    }
}