package utils;

import com.graphhopper.routing.util.VehicleEncodedValuesFactory;
import entities.TransportType;

public class Conversions
{
    public static String formatTime(double timeInSeconds)
    {
        int hours = (int)(timeInSeconds/60)/60;
        int minutes= (int) (timeInSeconds/60);
        int seconds= (int) (timeInSeconds%60);
        return hours + " hours " + minutes + " minutes and "+ seconds;
    }

    public static String toKm(double distance)
    {
        double km=distance/1000;
        return km + "" +
                " km";
    }
    public static double secondsToMinutes(double timeInSeconds){
        return timeInSeconds/60;
    }

    public static String toProfile(TransportType transportType)
    {
        switch (transportType)
        {
            case FOOT ->
            {
                return VehicleEncodedValuesFactory.FOOT;
            }
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
                return VehicleEncodedValuesFactory.WHEELCHAIR;
            }
        }
    }
}