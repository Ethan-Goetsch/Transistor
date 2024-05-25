package entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Journey
{
    private List<Trip> trips;
    private double totalTravelTime;

    public Journey() {
        this.trips = new ArrayList<>();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void addTrip(Trip trip){
        this.trips.add(trip);
    }

    public LocalTime getArrivalTime()
    {
        return trips.getLast().getArrivalTime();
    }

    public double getTotalTravelTime(){
        for (Trip trip: trips){
            totalTravelTime += trip.getTravelTime();
        }
        return totalTravelTime;
    }
}