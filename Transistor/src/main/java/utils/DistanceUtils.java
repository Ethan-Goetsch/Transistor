package utils;

import entities.Coordinate;

public class DistanceUtils
{
    private static final int radiusEarthInKM = 6371;

    public static double gcdistanceMeters(Coordinate point1, Coordinate point2)
    {
        double lat1 = Math.toRadians(point1.getLatitude());
        double lon1 = Math.toRadians(point1.getLongitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double lon2 = Math.toRadians(point2.getLongitude());

        double ret = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * radiusEarthInKM;
        return ret*1000;
    }

    public static void main(String[] args)
    {
        System.out.println(gcdistanceMeters(new Coordinate(51.0, 21.4), new Coordinate(10.3, 30)));
    }
}
