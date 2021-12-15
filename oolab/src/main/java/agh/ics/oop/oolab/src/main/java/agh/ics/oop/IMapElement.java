package agh.ics.oop;

public interface IMapElement {
    IWorldMap getMap();
    void setMap(IWorldMap map);
    Vector2d getPosition();
    boolean isAt(Vector2d position);
    String toString();
    boolean takesSpace();
}
