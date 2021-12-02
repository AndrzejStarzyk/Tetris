package agh.ics.oop;

import java.util.HashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected Vector2d upperRightBorder;
    protected Vector2d lowerLeftBorder;
    protected HashMap<Vector2d, IMapElement> mapElements;

    AbstractWorldMap(int width, int height){
        upperRightBorder = new Vector2d(width, height);
        lowerLeftBorder = new Vector2d(0, 0);
        mapElements = new HashMap<Vector2d, IMapElement>();
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        IMapElement element = mapElements.get(oldPosition);
        mapElements.remove(oldPosition);
        mapElements.put(newPosition, element);
    }

    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(lowerLeftBorder, upperRightBorder);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        return mapElements.get(position);
    }

}
