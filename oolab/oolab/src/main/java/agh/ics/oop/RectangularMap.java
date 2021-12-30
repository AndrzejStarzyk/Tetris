package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap implements IWorldMap{
    protected Vector2d upperRightBorder;
    protected Vector2d lowerLeftBorder;

    public RectangularMap(int width, int height){
        super();
        upperRightBorder = new Vector2d(width-1, height-1);
        lowerLeftBorder = new Vector2d(0, 0);
    }

    public boolean canMoveTo(Vector2d position){
        return position.correctPosition(lowerLeftBorder, upperRightBorder) && !isOccupied(position);
    }

    public boolean place(Animal animal) throws IllegalArgumentException{
        Vector2d position = animal.getPosition();
        if(!this.canMoveTo(position)){
            throw new IllegalArgumentException("Animal can't be placed on position " + position.toString());
        }
        animal.setMap(this);
        animal.addObserver(this);
        animal.addObserver(mapBoundary);
        mapElements.put(position, animal);
        mapBoundary.xElements.put(position, animal);
        mapBoundary.yElements.put(position, animal);
        return true;
    }
    }