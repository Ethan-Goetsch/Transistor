package entities.exceptions;

public class RouteNotFoundException extends Exception
{
    public RouteNotFoundException(String errorMessage){
        super(errorMessage);
    }
}