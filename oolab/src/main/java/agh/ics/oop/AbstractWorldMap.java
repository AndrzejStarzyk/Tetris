package agh.ics.oop;

import java.util.HashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected HashMap<Vector2d, IMapElement> mapElements;
    protected MapBoundary mapBoundary;

    AbstractWorldMap(){
        mapElements = new HashMap<Vector2d, IMapElement>();
        mapBoundary = new MapBoundary(this);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        IMapElement element = mapElements.get(oldPosition);
        mapElements.remove(oldPosition);
        mapElements.put(newPosition, element);
    }

    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        if(mapBoundary.xElements.isEmpty()){
            return mapVisualizer.draw(new Vector2d(0,0), new Vector2d(0,0));
        }
        return mapVisualizer.draw(new Vector2d(mapBoundary.xElements.firstKey().x,
                        mapBoundary.yElements.firstKey().y),
                new Vector2d(mapBoundary.xElements.lastKey().x,
                        mapBoundary.yElements.lastKey().y));
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        return mapElements.get(position);
    }
}
