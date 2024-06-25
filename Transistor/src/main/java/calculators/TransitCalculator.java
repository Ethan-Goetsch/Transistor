package calculators;

import entities.TransitType;
import entities.Trip;

import java.time.LocalTime;
import java.util.List;

public abstract class TransitCalculator
{
    public abstract TransitType getTransitType();
    public abstract List<Trip> calculateRoute(int originId, int destinationId, LocalTime searchTime) throws Exception;
}