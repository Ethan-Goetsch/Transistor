package entities;

public enum TransportType
{
    FOOT(4.5),
    BIKE(16),
    CAR(22.5),
    BUS(45);

    private final double speedInKilometersPerHour;
    private TransportType(double speedInKilometersPerSecond)
    {
        this.speedInKilometersPerHour = speedInKilometersPerSecond;
    }

    public double getSpeedInKilometersPerHour() { return speedInKilometersPerHour; }
}