package entities.TransitGraphEntities;

import java.util.List;

public class TShape
{
    private int id;
    private List<TShapePoint> shapePoints;
    
    public TShape(int id, List<TShapePoint> shapePoints)
    {
        this.id = id;
        this.shapePoints = shapePoints;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public List<TShapePoint> getShapePoints()
    {
        return shapePoints;
    }

    public void setShapePoints(List<TShapePoint> shapePoints)
    {
        this.shapePoints = shapePoints;
    }
}
