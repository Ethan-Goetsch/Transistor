package entities;

public enum TransportType
{
    FOOT(4.5),
    BIKE(16),
    CAR(22.5),
    BUS(45);

    private final double speedInKilometersPerHour;
    private TransportType(double speedInKilometersPerHour)
    {
        this.speedInKilometersPerHour = speedInKilometersPerHour;
    }

    public double getSpeedInKilometersPerHour() { return speedInKilometersPerHour; }
}