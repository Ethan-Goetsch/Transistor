package entities;

import java.util.List;

public record AccessibilityMeasure (List<Double> indexes, Coordinate postalCodeLocation, String message){
}
