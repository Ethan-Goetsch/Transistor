import application.ApplicationManager;
import calculators.AerialCalculator;
import resolvers.LocationResolver;
import ui.UIController;
import utils.PathLocations;

public class Program
{
    public static void main(String[] args)
    {
        LocationResolver locationResolver = new LocationResolver(PathLocations.MASS_LOCATION_FILE);
        ApplicationManager manager = new ApplicationManager(locationResolver);
        UIController controller = new UIController(manager);
    }
}