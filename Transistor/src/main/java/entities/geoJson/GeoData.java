package entities.geoJson;

public class GeoData {
    private String type;
    private double latitude;
    private double longitude;

    public GeoData(String type, double latitude, double longitude){
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

