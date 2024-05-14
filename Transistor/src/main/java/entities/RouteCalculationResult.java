package entities;

import java.util.ArrayList;

public record RouteCalculationResult(Path path, double distanceInKM, double timeInHours)
{
    public RouteCalculationResult merge(RouteCalculationResult result, boolean append)
    {
        var newPoints = new ArrayList<PathPoint>();
        newPoints.addAll(path.points());
        newPoints.addAll(append ? newPoints.size() - 1 : 0, result.path.points());

        var newPath = new Path(newPoints);
        var newDistance = distanceInKM + result.distanceInKM();
        var newTime = timeInHours + result.timeInHours;

        return new RouteCalculationResult(newPath, newDistance, newTime);
    }
}