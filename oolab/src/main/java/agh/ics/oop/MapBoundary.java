package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapBoundary implements IPositionChangeObserver{
    public SortedMap<Vector2d, Object> xElements;
    public SortedMap<Vector2d, Object> yElements;
    public IWorldMap map;

    public MapBoundary(IWorldMap map){
        this.map = map;
        xElements = new TreeMap<Vector2d, Object>(new Comparator<Vector2d>() {
            public int compare(Vector2d o1, Vector2d o2) {

                if(o1.x < o2.x){
                    return -1;
                }
                if(o1.x == o2.x && o1.y < o2.y){
                    return -1;
                }
                if(o2.x < o1.x){
                    return 1;
                }
                if(o2.x == o1.x && o2.y < o1.y) {
                    return 1;
                }
                if(map.objectAt(o1) instanceof Animal){
                    return 1;
                }
                if(map.objectAt(o2) instanceof Grass){
                    return -1;
                }
                return 0;
            }
        });
        yElements = new TreeMap<Vector2d, Object>(new Comparator<Vector2d>() {
            public int compare(Vector2d o1, Vector2d o2) {
                if(o1.y < o2.y){
                    return -1;
                }
                if(o1.y == o2.y && o1.x < o2.x){
                    return -1;
                }
                if(o2.y < o1.y){
                    return 1;
                }
                if(o2.y == o1.y && o2.x < o1.x) {
                    return 1;
                }
                if(map.objectAt(o1) instanceof Animal){
                    return 1;
                }
                if(map.objectAt(o2) instanceof Grass){
                    return -1;
                }
                return 0;
            }
        });
    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        xElements.remove(oldPosition);
        yElements.remove(oldPosition);
        Object element = map.objectAt(newPosition);
        xElements.put(newPosition, element);
        yElements.put(newPosition, element);

    }
}
