package entities;

public enum TransportType
{
    FOOT(1.42),
    BIKE(7),
    CAR(22.5),
    PLANE(268.224000);

    private double speedInMetersPerSecond;
    private TransportType(double speedInMetersPerSecond)
    {
        this.speedInMetersPerSecond = speedInMetersPerSecond;
    }

    public double getSpeedInMetersPerSecond() { return speedInMetersPerSecond; }
}