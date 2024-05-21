package entities;

public enum TransportType
{
    FOOT(4.5),
    BIKE(16),
    CAR(22.5),
    BUS(45);

    private final double speedInKilometersPerSecond;
    private TransportType(double speedInKilometersPerSecond)
    {
        this.speedInKilometersPerSecond = speedInKilometersPerSecond;
    }

    public double getSpeedInKilometersPerSecond() { return speedInKilometersPerSecond; }
}