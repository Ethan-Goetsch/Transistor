package calculators;

import entities.RouteCalculationRequest;
import entities.Trip;
import entities.RouteType;

public interface IRouteCalculator
{
   public RouteType getRouteType();
   public Trip calculateRoute(RouteCalculationRequest calculationRequest);

}