package entities;

import java.util.ArrayList;

public record RouteCalculationResult(Path path, double distanceInKM, double timeInHours)
{
    public RouteCalculationResult merge(RouteCalculationResult result)
    {
        var newPoints = new ArrayList<PathPoint>();
        newPoints.addAll(0, result.path.points());
        newPoints.addAll(path.points());

        var newPath = new Path(newPoints);
        var newDistance = distanceInKM + result.distanceInKM();
        var newTime = timeInHours + result.timeInHours;

        return new RouteCalculationResult(newPath, newDistance, newTime);
    }
}