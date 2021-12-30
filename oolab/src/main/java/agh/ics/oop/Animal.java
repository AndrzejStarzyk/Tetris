package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class Animal extends AbstractWorldMapElement implements IMapElement{
    private MapDirection direction;
    private final List<IPositionChangeObserver> observers = new LinkedList<>();

    public Animal(IWorldMap map, Vector2d position, MapDirection direction){
        super(position);
        this.map = map;
        this.direction = direction;
        map.place(this);
    }

    public MapDirection getDirection(){
        return direction;
    }

    public String toString(){
        return direction.toString();
    }

    public String toStringWithPosition(){
        return "Z " + position.toString();
    }

    public boolean takesSpace() {
        return true;
    }

    public void moveUtil(boolean dir){
        Vector2d newPosition = dir ? position.add(direction.toUnitVector()) : position.subtract(direction.toUnitVector());
        if (map.canMoveTo(newPosition)) {
            Vector2d oldPosition = new Vector2d(position.x, position.y);
            position = newPosition;
            this.positionChanged(oldPosition, newPosition);
        }
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> this.moveUtil(true);
            case BACKWARD -> this.moveUtil(false);
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public String getImage(){
        return switch (this.getDirection()){
            case NORTH -> "src/main/resources/up.png";
            case EAST -> "src/main/resources/right.png";
            case SOUTH -> "src/main/resources/down.png";
            case WEST -> "src/main/resources/left.png";
        };
    }
}
