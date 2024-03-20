package calculators;

import utils.TransportationType;
import static utils.TransportationTypeCreated.*;

public class RouteCalculator
{
    private static final int radiusEarthInM = 6371000;

    public double Distance2P(double lat1, double lon1, double lat2, double lon2)
    {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        double distanceAerial = Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*radiusEarthInM;
        return  distanceAerial;
    }
//    public double KilometerTimeTaken(double distance)
//    {
//        double kiloPerHour=(distance/1000)/5;
//        return kiloPerHour;
//    }
//    public double MeterTimeTaken(double distance)
//    {
//        double MeterPerSecond=distance/1.4;
//        return MeterPerSecond;
//    }

    public double calculateTime(TransportationType tran, double lat1, double lon1, double lat2, double lon2){
        double distance= Distance2P( lat1, lon1, lat2, lon2);
        return calculateTime(tran, distance);
    }
    public double calculateTime(TransportationType tran, double distance){
        double speed= tran.getSpeed();
        double time=distance/speed;
        return time;
    }
    public static void main(String[] args)
    {
        RouteCalculator calculator = new RouteCalculator();
        double distance=calculator.Distance2P(50.82323604907065, 5.78925627243624, 50.81945728841596, 5.8161612938171485);
        System.out.println(distance);

        double time=calculator.calculateTime(human,distance);
        System.out.println(time/60);
    }
}