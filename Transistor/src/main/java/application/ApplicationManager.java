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
            departureCoordinates = locationResolver.getCordsFromPostCode(request.departure());
            arrivalCoordinates = locationResolver.getCordsFromPostCode(request.arrival());

            var originStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(departureCoordinates, 10));
            var destinationStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(arrivalCoordinates, 10));
            journey = getRouteCalculationResult(request, departureCoordinates, arrivalCoordinates, originStops, destinationStops);

            if (journey == null || journey.getTrips().isEmpty())
            {
                throw new RouteNotFoundException("No route found");
            }
        }
        catch (Exception e)
        {
            message = e.getMessage();
            e.printStackTrace();
        }

        return new Route(departureCoordinates, arrivalCoordinates, journey, message);
    }

    private Journey getRouteCalculationResult(RouteRequest request, Coordinate departureCoordinates, Coordinate arrivalCoordinates, List<TransitStop> originStops, List<TransitStop> destinationStops)
    {
        Journey earliestJourney = null;
        double shortestTravelTime = Double.MAX_VALUE;

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

                double journeyTravelTime = journey.getTotalTravelTime();

                if (journeyTravelTime < shortestTravelTime)
                {
                    earliestJourney = journey;
                    shortestTravelTime = journeyTravelTime;
                }
            }
        }

        return earliestJourney;
    }
}