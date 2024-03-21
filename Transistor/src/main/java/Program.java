import application.ApplicationManager;
import application.RequestValidator;
import calculators.AerialCalculator;
import resolvers.LocationResolver;
import ui.UIController;
import utils.PathLocations;

public class Program
{
    public static void main(String[] args)
    {
        LocationResolver locationResolver = new LocationResolver(PathLocations.MASS_LOCATION_FILE);
        RequestValidator requestValidator = new RequestValidator();
        ApplicationManager manager = new ApplicationManager(locationResolver, requestValidator);
        UIController controller = new UIController(manager);
    }
}