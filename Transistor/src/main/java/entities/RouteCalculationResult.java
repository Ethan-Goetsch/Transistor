package entities;

import com.graphhopper.ResponsePath;

public record RouteCalculationResult(ResponsePath path, double distanceInKM, double timeInMinutes)
{
}