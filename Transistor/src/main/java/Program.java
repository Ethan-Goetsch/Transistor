import application.ApplicationManager;
import calculators.RouteCalculator;
import resolvers.LocationResolver;
import ui.UIController;
import utils.PathLocations;

public class Program
{
    public static void main(String[] args)
    {
        LocationResolver locationResolver = new LocationResolver(PathLocations.MASS_LOCATION_FILE);
        RouteCalculator routeCalculator = new RouteCalculator();
        ApplicationManager manager = new ApplicationManager(locationResolver, routeCalculator);
        UIController controller = new UIController(manager);
    }
}