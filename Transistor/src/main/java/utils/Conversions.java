package utils;

import com.graphhopper.ResponsePath;
import com.graphhopper.routing.util.VehicleEncodedValuesFactory;
import entities.Coordinate;
import entities.Path;
import entities.Point;
import entities.TransportType;

import java.util.ArrayList;

public class Conversions
{
    public static String formatTime(double totalHours)
    {
        int hours = (int) totalHours;
        int minutes = (int) ((totalHours % 1) * 60);
        int seconds = (int) ((totalHours % 1) * 3600) % 60;

        return hours + " hours " + minutes + " minutes " + seconds + " seconds";
    }

    public static String formatDistance(double distanceInKilometers)
    {
        return String.format("%.2f", distanceInKilometers) + " KM";
    }

    public static double metersToKilometers(double meters)
    {
        return meters/1000;
    }

    public static double calculateTime(double distanceInKilometers, TransportType type)
    {
        double speed = type.getSpeedInKilometersPerSecond();
        return distanceInKilometers/speed;
    }

    public static Path toPath(ResponsePath graphHopperPath)
    {
        var points = new ArrayList<Point>();
        var specialPoints = new ArrayList<Point>();
        for (var i = 0; i < graphHopperPath.getPoints().size(); i++)
        {
            var ghPoint = graphHopperPath.getPoints().get(i);
            Point point;
            if(false){//special
                int id = -1;//todo change that to the id and find a way to distinguish points
                point = new Point(new Coordinate(ghPoint.lat, ghPoint.lon), id);
                specialPoints.add(point);
            }else{
                point = new Point(new Coordinate(ghPoint.lat, ghPoint.lon));
            }
            points.add(point);
        }
        return new Path(points,specialPoints);
    }

    public static String toProfile(TransportType transportType)
    {
        switch (transportType)
        {
            case BIKE ->
            {
                return VehicleEncodedValuesFactory.BIKE;
            }
            case CAR ->
            {
                return VehicleEncodedValuesFactory.CAR;
            }
            default ->
            {
                return VehicleEncodedValuesFactory.FOOT;
            }
        }
    }
}