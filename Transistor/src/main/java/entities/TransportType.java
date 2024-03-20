package entities;

import utils.TransportationType;

public enum TransportType
{
    WALKING(1.42),
    BICYCLE(7),
    CAR(22.5),
    PlANE(268.224000);

    private double speedInMetersPerSecond;
    private TransportType(double speedInMetersPerSecond)
    {
        this.speedInMetersPerSecond = speedInMetersPerSecond;
    }

    public double getSpeedInMetersPerSecond() { return speedInMetersPerSecond; }
}