package utils;

import com.graphhopper.ResponsePath;
import com.graphhopper.routing.util.VehicleEncodedValuesFactory;
import entities.*;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class Conversions
{
    public static int localTimeToInt(LocalTime localTime)
    {
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();

        int totalSeconds = hour * 3600 + minute * 60 + second;

        return totalSeconds;
    }

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

    public static Path toPath(ResponsePath graphHopperPath, Color color)
    {
        var points = new ArrayList<PathPoint>();
        for (var i = 0; i < graphHopperPath.getPoints().size(); i++)
        {
            var ghPoint = graphHopperPath.getPoints().get(i);
            var point = new PathPoint(new Coordinate(ghPoint.lat, ghPoint.lon), PointType.Normal);
            points.add(point);
        }
        return new Path(points, color);
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