package agh.ics.oop.map;

import agh.ics.oop.IAnimalChangeObserver;
import agh.ics.oop.Vector2d;

public class UnboundedMap extends AbstractMap implements IMap, IAnimalChangeObserver {
    public UnboundedMap(int width, int height) {
        super(width, height);
    }

    @Override
    public Vector2d NewPosition(Vector2d oldPosition, Vector2d r) {
        Vector2d newPosition = oldPosition.add(r);
        int newX = newPosition.x < 0 ? newPosition.x + upperRight.x + 1 :
                newPosition.x > upperRight.x ? newPosition.x - upperRight.x - 1 : newPosition.x;
        int newY = newPosition.y < 0 ? newPosition.y + upperRight.y + 1 :
                newPosition.y > upperRight.y ? newPosition.y - upperRight.y - 1 : newPosition.y;
        return new Vector2d(newX, newY);
    }


}
