package database;

import entities.Coordinate;

public class RadiusGenerator {
    public static void main(String[] args) {
        double[][] d = getRadius(new Coordinate(51.93752, 4.384413));
        System.out.println(d);
    }

    public static double[][] getRadius(Coordinate coordinate){
        double lat = coordinate.getLatitude();
        double lon = coordinate.getLongitude();
        double earthRadius = 6371 * 1000;
        double distance = 100;
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
