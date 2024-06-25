package application;

import accessibility.IndexCalculator;
import calculators.AerialCalculator;
import calculators.TransitCalculator;
import database.DatabaseManager;
import database.queries.GetClosetStops;
import entities.*;
import entities.exceptions.AccessibilityCalculationError;
import entities.exceptions.InvalidCoordinateException;
import entities.exceptions.RouteNotFoundException;
import entities.geoJson.GeoDeserializer;
import entities.transit.TransitStop;
import resolvers.LocationResolver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ApplicationManager
{
    private final LocationResolver locationResolver;
    private final RequestValidator requestValidator;
    private final IndexCalculator accessibilityCalculator;
    private final List<TransitCalculator> transitCalculators;
    private final AerialCalculator aerialCalculator;

    public ApplicationManager(LocationResolver locationResolver, RequestValidator requestValidator, IndexCalculator accessibilityCalculator, List<TransitCalculator> transitCalculators, AerialCalculator aerialCalculator)
    {
        this.locationResolver = locationResolver;
        this.requestValidator = requestValidator;
        this.accessibilityCalculator = accessibilityCalculator;
        this.transitCalculators = transitCalculators;
        this.aerialCalculator = aerialCalculator;
    }

    public AccessibilityMeasure calculateAccessibilityMeasure(AccessibilityRequest request)
    {
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

            indexes = accessibilityCalculator.calculateIndex(GeoDeserializer.deserializeAllGeoData("Transistor/src/main/resources/geoJson/amenity.geojson", "Transistor/src/main/resources/geoJson/shop.geojson", "Transistor/src/main/resources/geoJson/tourism.geojson"),postalCodeLocation, request.disabledPersonSetting(), request.locationNumberSensitivity(), request.transportType());
            if (indexes == null)
            {
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

    public Trip calculateAerialTrip(RouteCalculationRequest request)
    {
        return aerialCalculator.calculateRoute(request);
    }

    public Route processRouteRequest(RouteRequest request)
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

            var originStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(departureCoordinates, 1));
            var destinationStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(arrivalCoordinates, 1));

           journey = calculateJourney(departureCoordinates, arrivalCoordinates, originStops, destinationStops, request.transportType(), request.transitType());

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

    private Journey calculateJourney(Coordinate departureCoordinates, Coordinate arrivalCoordinates, List<TransitStop> originStops, List<TransitStop> destinationStops, TransportType transportType, TransitType transitType) throws Exception
    {
        Journey earliestJourney = null;
        var shortestTravelTime = Double.MAX_VALUE;

        for (var originStop : originStops)
        {
            for (var destinationStop : destinationStops)
            {
                var journey = new Journey();
                // Calculate route from starting location to origin bus stop
                var locationToOriginTrip = aerialCalculator.calculateRoute(
                        new RouteCalculationRequest(departureCoordinates,
                                originStop.coordinate(),
                                LocalTime.NOON,
                                transportType));

                // Calculate the actual bus trip from starting bus stop to the final bus stop
                var transitTrip = transitCalculators.stream()
                        .filter(calculator -> calculator.getTransitType() == transitType)
                        .findFirst()
                        .orElseThrow()
                        .calculateRoute(originStop.id(), destinationStop.id(), locationToOriginTrip.getArrivalTime());

                if (transitTrip == null)
                {
                    continue;
                }

                // Calculate route from destination bus stop to final destination
                var destinationToFinal = aerialCalculator.calculateRoute(
                        new RouteCalculationRequest(destinationStop.coordinate(),
                                arrivalCoordinates,
                                transitTrip.getLast().getArrivalTime(),
                                transportType));

                journey.addTrip(locationToOriginTrip);
                journey.addTrip(transitTrip);
                journey.addTrip(destinationToFinal);

                var journeyTravelTime = journey.getTotalTravelTime();
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