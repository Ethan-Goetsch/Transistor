package entities;

import utils.Coordinates;

public record Route(Coordinates departure, Coordinates arrival, double distance, double time, TransportType transportType, String responseMessage)
{
}
