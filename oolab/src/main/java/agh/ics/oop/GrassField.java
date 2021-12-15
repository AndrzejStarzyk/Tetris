package agh.ics.oop;

import java.util.Random;
import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap implements IWorldMap{
    private final Vector2d grassUpperRightBorder;

    public GrassField(int numberOfGrassFields){
        super();
        int pom = (int)(sqrt(numberOfGrassFields*10));
        grassUpperRightBorder = new Vector2d(pom, pom);

        for(int i=0;i<numberOfGrassFields;i++){

            this.placeGrass();
        }
    }

    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) || (this.isOccupied(position) && !mapElements.get(position).takesSpace());
    }

    public boolean placeGrass(){
        Vector2d v;
        Random random = new Random();
        do{
            v = new Vector2d(random.nextInt(grassUpperRightBorder.x), random.nextInt(grassUpperRightBorder.y));
        }
        while (this.isOccupied(v));

        Grass grass = new Grass(v);
        grass.setMap(this);
        mapElements.put(grass.getPosition(), grass);
        mapBoundary.xElements.put(v, grass);
        mapBoundary.yElements.put(v, grass);
        return true;
    }

    public boolean place(Animal animal) throws IllegalArgumentException{
        Vector2d position = animal.getPosition();

        if(!this.canMoveTo(position)){
            throw new IllegalArgumentException("Animal can't be placed on position " + position.toString());
        }
        if(isOccupied(position)){
            this.placeGrass();
            mapElements.remove(position);
        }
        animal.addObserver(this);
        animal.addObserver(mapBoundary);
        mapElements.put(animal.getPosition(), animal);
        mapBoundary.xElements.put(animal.getPosition(), animal);
        mapBoundary.yElements.put(animal.getPosition(), animal);
        return true;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if(this.isOccupied(newPosition)){
            this.placeGrass();
            mapElements.remove(newPosition);
        }
        super.positionChanged(oldPosition, newPosition);
        mapBoundary.positionChanged(oldPosition, newPosition);
    }
}
