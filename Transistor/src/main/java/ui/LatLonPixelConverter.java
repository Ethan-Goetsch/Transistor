package ui;
public class LatLonPixelConverter {
    double eastBound = 5.7533842;
    double westBound = 5.64212966666666;
    double northBound = 50.9007405;
    double southBound = 50.8158155722222;

    public int convertLongitudeToPixel(double longitude, int panelWidth) {
        double longitudeRange = eastBound - westBound;
        double normalizedPosition = (longitude - westBound) / longitudeRange;
        return (int) (normalizedPosition * panelWidth);
    }

    public int convertLatitudeToPixel(double latitude, int panelHeight) {
        double latitudeRange = northBound - southBound;
        double normalizedPosition = (latitude - southBound) / latitudeRange;
        return (int) ((1 - normalizedPosition) * panelHeight);
    }
}
