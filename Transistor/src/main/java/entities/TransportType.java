package entities;

public enum TransportType
{
    FOOT(0.00139),
    BIKE(0.00417),
    CAR(0.01667),
    BUS(0.01111);

    private final double speedInKilometersPerSecond;
    private TransportType(double speedInKilometersPerSecond)
    {
        this.speedInKilometersPerSecond = speedInKilometersPerSecond;
    }

    public double getSpeedInKilometersPerSecond() { return speedInKilometersPerSecond; }
}