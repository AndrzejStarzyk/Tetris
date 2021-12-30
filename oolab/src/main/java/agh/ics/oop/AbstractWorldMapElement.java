package agh.ics.oop;

public abstract class AbstractWorldMapElement{
    protected IWorldMap map;
    protected Vector2d position;


    AbstractWorldMapElement(Vector2d position){
        this.position = position;
    }

    public IWorldMap getMap() {
        return map;
    }

    public void setMap(IWorldMap map) {
        this.map = map;
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position) {
        return this.getPosition().equals(position);
    }
}
