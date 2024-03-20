import application.ApplicationManager;
import calculators.RouteCalculator;
import resolvers.LocationResolver;
import ui.UIController;

public class Program
{
    public static void main(String[] args)
    {
        LocationResolver locationResolver = new LocationResolver("Transistor/src/main/resources/MassZipLatLon.xlsx");
        RouteCalculator routeCalculator = new RouteCalculator();
        ApplicationManager manager = new ApplicationManager(locationResolver, routeCalculator);
        UIController controller = new UIController(manager);
    }
}