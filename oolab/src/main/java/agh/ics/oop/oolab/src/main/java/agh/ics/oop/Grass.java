package agh.ics.oop;

public class Grass extends AbstractWorldMapElement implements IMapElement{
    public Grass(Vector2d position){
        super(position);
    }

    public String toString() {
        return "*";
    }

    public boolean takesSpace() {
        return false;
    }
}
