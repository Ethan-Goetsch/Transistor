package calculators;

public class RouteCalculator
{
    private static final int radiusEarthInM = 6371000;
    private double lat1;
    private double lon1;
    private double lat2;
    private double lon2;

    public RouteCalculator(double lat1, double lon1, double lat2,  double lon2)
    {
        this.lat1 = lat1;
        this.lon1 = lon1;
        this.lat2 = lat2;
        this.lon2 = lon2;

    }

    public double Distance2P(double lat1, double lon1, double lat2, double lon2)
    {

        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double distanceAerial = Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*radiusEarthInM;
        return  distanceAerial;
    }

    public double KilometerTimeTaken(double distance)
    {
        double kiloPerHour=(distance/1000)/5;
        return kiloPerHour;
    }

    public double MeterTimeTaken(double distance)
    {
        double MeterPerSecond=distance/1.4;
        return MeterPerSecond;
    }

    public static void main(String[] args)
    {
        RouteCalculator calculator = new RouteCalculator(50.79895451821053, 5.781389348130654, 50.80355868541156, 5.801989002844258);
        double distance=calculator.Distance2P(50.79895451821053, 5.781389348130654, 50.80355868541156, 5.801989002844258);
        System.out.println(distance);
        double SecondstimeTaken= calculator.MeterTimeTaken(distance);
        double HourstimeTaken= calculator.KilometerTimeTaken(distance);

        System.out.println(SecondstimeTaken);
        System.out.println(HourstimeTaken);
    }
}