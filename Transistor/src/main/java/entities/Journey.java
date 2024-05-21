package entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Journey {
    private List<Trip> trips;

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

    public LocalTime getArrivalTime(){
        return LocalTime.parse(trips.getLast().getArrivalDescription());
    }
}