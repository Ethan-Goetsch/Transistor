package calculators;

import entities.RouteCalculationRequest;
import entities.RouteCalculationResult;

public interface ICalculator {
   public RouteCalculationResult calculateRoute(RouteCalculationRequest calculationRequest);

}
