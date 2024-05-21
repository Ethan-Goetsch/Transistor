package application;

import entities.Coordinate;

public class RadiusGenerator
{
    public static double[][] getRadius(Coordinate coordinate, double distance)
    {
        double lat = coordinate.getLatitude();
        double lon = coordinate.getLongitude();
        double earthRadius = 6371 * 1000;
        double deltaLat = distance / earthRadius;
        deltaLat = Math.toDegrees(deltaLat);

        double deltaLon = distance / (earthRadius * Math.cos(Math.toRadians(lat)));
        deltaLon = Math.toDegrees(deltaLon);

        // New coordinates
        double northLat = lat + deltaLat;
        double southLat = lat - deltaLat;
        double eastLon = lon + deltaLon;
        double westLon = lon - deltaLon;
        return new double[][]{{southLat, northLat},{westLon, eastLon}};
    }
}