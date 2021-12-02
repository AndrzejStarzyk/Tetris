package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap implements IWorldMap{
    public RectangularMap(int width, int height){
        super(width, height);
    }

    public boolean canMoveTo(Vector2d position){
        return position.correctPosition(lowerLeftBorder, upperRightBorder) && !isOccupied(position);
    }

    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();
        if(!position.correctPosition(lowerLeftBorder, upperRightBorder)){
            return false;
        }
        if(this.canMoveTo(position)){
            animal.setMap(this);
            animal.addObserver(this);
            mapElements.put(position, animal);
            return true;
        }
        return false;
    }
    }