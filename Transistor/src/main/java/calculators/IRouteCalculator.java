package calculators;

import entities.RouteCalculationRequest;
import entities.RouteCalculationResult;
import entities.RouteType;

public interface IRouteCalculator
{
   public RouteType getRouteType();
   public RouteCalculationResult calculateRoute(RouteCalculationRequest calculationRequest);

}
