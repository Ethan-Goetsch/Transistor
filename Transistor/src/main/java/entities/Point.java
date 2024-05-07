package entities;

public class Point {
    private Coordinate coordinate;
    private int ID;
    private boolean isSpecialPoint;
    public Point(Coordinate coordinate){
        this.coordinate = coordinate;
        this.ID = -1;
        this.isSpecialPoint = false;
    }

    public Point(Coordinate coordinate, int ID){
        this(coordinate);
        this.ID = ID;
        this.isSpecialPoint = true;
    }

    public Coordinate getCoordinate(){
        return this.coordinate;
    }
    public int getID(){
        return this.ID;
    }
    public boolean isSpecial(){
        return isSpecialPoint;
    }
}
