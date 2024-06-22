import accessibility.IndexCalculator;
import application.ApplicationManager;
import application.RequestValidator;
import calculators.*;
import entities.geoJson.GeoDeserializer;
import resolvers.LocationResolver;
import ui.UIController;
import utils.PathLocations;

import java.util.ArrayList;

public class Program
{
    //6211AL	50.85523285	5.692237193
    //6216NV	50.84421547	5.655460167
    //6211AM
    
    // test
    public static void main(String[] args)
    {
        LocationResolver locationResolver = new LocationResolver(PathLocations.MASS_LOCATION_FILE);
        RequestValidator requestValidator = new RequestValidator();

        IndexCalculator accessibilityCalculator = new IndexCalculator();
        TransitGraphCalculator transitGraphCalculator = new TransitGraphCalculator();

        var transitCalculators = new ArrayList<TransitCalculator>();
        transitCalculators.add(new DirectTransitCalculator());
        transitCalculators.add(new TransferTransitCalculator(transitGraphCalculator));

        AerialCalculator aerialCalculator = new AerialCalculator();
        GeoDeserializer geoDeserializer = new GeoDeserializer();

        ApplicationManager manager = new ApplicationManager(locationResolver, requestValidator, accessibilityCalculator, transitCalculators, aerialCalculator);
        accessibilityCalculator.setManager(manager);
        UIController controller = new UIController(manager);
    }
}