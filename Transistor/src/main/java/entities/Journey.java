package entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Journey
{
    private final List<Trip> trips;
    private final double totalTravelTime;

    public Journey()
    {
        this.trips = new ArrayList<>();
        this.totalTravelTime = calculateTotalTravelTime();
    }

    public Journey(List<Trip> trips)
    {
        this.trips = trips;
        this.totalTravelTime = calculateTotalTravelTime();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void addTrip(Trip trip)
    {
        this.trips.add(trip);
    }

    public void addTrip(List<Trip> trip)
    {
        this.trips.addAll(trip);
    }

    public LocalTime getArrivalTime()
    {
        return trips.getLast().getArrivalTime();
    }

    public double getTotalTravelTime()
    {
        return totalTravelTime;
    }

    private double calculateTotalTravelTime()
    {
        var time = 0.0;
        for (Trip trip: trips)
        {
            time += trip.getTravelTimeHours();
        }
        return time;
    }
}