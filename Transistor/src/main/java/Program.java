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
        AerialCalculator aerialCalculator = new AerialCalculator();
        ApplicationManager manager = new ApplicationManager(locationResolver, aerialCalculator);
        UIController controller = new UIController(manager);
    }
}