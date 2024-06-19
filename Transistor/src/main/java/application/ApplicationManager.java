package application;

import Accessibility.IndexCalculator;
import calculators.AerialCalculator;
import calculators.IRouteCalculator;
import calculators.TransitCalculator;
import database.DatabaseManager;
import database.queries.GetClosetStops;
import entities.*;
import entities.exceptions.*;
import entities.geoJson.GeoDeserializer;
import entities.transit.TransitStop;
import resolvers.LocationResolver;

import java.util.List;

public class ApplicationManager {
    private final LocationResolver locationResolver;
    private final RequestValidator requestValidator;
    private final List<IRouteCalculator> routeCalculators;
    private final IndexCalculator accessibilityCalculator;
    private final GeoDeserializer geoDeserializer;

    public ApplicationManager(LocationResolver locationResolver, RequestValidator requestValidator, List<IRouteCalculator> routeCalculators, IndexCalculator accessibilityCalculator, GeoDeserializer geoDeserializer) {
        this.locationResolver = locationResolver;
        this.requestValidator = requestValidator;
        this.routeCalculators = routeCalculators;
        this.accessibilityCalculator = accessibilityCalculator;
        this.geoDeserializer = geoDeserializer;
    }

    public AccessibilityMeasure calculateAccessibilityMeasure(AccessibilityRequest request){
        String message = "";
        List<Double> indexes = null;
        Coordinate postalCodeLocation = null;

        if (!requestValidator.isValidRequest(request))
        {
            return new AccessibilityMeasure(null,null,"Invalid Input");
        }

        try
        {
            postalCodeLocation = locationResolver.getCordsFromPostCode(request.postalCode());


            if (postalCodeLocation == null )
            {
                throw new InvalidCoordinateException("Invalid Input!");
            }
            indexes = accessibilityCalculator.calculateIndex(geoDeserializer.deserializeAllGeoData(),postalCodeLocation, request.disabledPersonSetting());
            if(indexes == null){
                throw new AccessibilityCalculationError("Error in accessibility calculation!");
            }
        }
        catch (Exception e)
        {
            message = e.getMessage();
            e.printStackTrace();
        }
        return new AccessibilityMeasure(indexes, postalCodeLocation, message);
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

           journey = getJourney(departureCoordinates, arrivalCoordinates, request);

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

    public Journey getJourney(Coordinate departureCoordinates, Coordinate arrivalCoordinates, RouteRequest request){
        Journey journey = null;
        var originStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(departureCoordinates, 10));
        var destinationStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(arrivalCoordinates, 10));
        journey = getRouteCalculationResult(request, departureCoordinates, arrivalCoordinates, originStops, destinationStops);
        return journey;
    }
}