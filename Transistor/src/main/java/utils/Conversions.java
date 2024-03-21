package utils;

import com.graphhopper.routing.util.VehicleEncodedValuesFactory;
import entities.TransportType;

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