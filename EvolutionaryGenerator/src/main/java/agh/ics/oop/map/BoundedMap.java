package agh.ics.oop.map;

import agh.ics.oop.IAnimalChangeObserver;
import agh.ics.oop.Vector2d;

public class BoundedMap extends AbstractMap implements IMap, IAnimalChangeObserver {
    public BoundedMap(int width, int height) {
        super(width, height);
    }

    @Override
    public Vector2d NewPosition(Vector2d oldPosition, Vector2d r) {
        Vector2d newPosition = oldPosition.add(r);
        if(newPosition.follows(lowerLeft) && newPosition.precedes(upperRight)){
            return newPosition;
        }
        return oldPosition;
    }
}
