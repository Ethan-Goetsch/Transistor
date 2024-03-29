import application.ApplicationManager;
import application.RequestValidator;
import calculators.AerialCalculator;
import calculators.IRouteCalculator;
import calculators.PathCalculator;
import resolvers.LocationResolver;
import ui.UIController;
import utils.PathLocations;

import java.util.ArrayList;
import java.util.List;

public class Program
{
    //6211AL	50.85523285	5.692237193
    //6216NV	50.84421547	5.655460167
    public static void main(String[] args)
    {
        LocationResolver locationResolver = new LocationResolver(PathLocations.MASS_LOCATION_FILE);
        RequestValidator requestValidator = new RequestValidator();

        List<IRouteCalculator> routeCalculators = new ArrayList<>();
        routeCalculators.add(new AerialCalculator());
        routeCalculators.add(new PathCalculator());

        ApplicationManager manager = new ApplicationManager(locationResolver, requestValidator, routeCalculators);
        UIController controller = new UIController(manager);
    }
}