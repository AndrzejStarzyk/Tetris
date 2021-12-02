package agh.ics.oop;

import java.util.Random;
import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap implements IWorldMap{
    private final Vector2d grassUpperRightBorder;

    public GrassField(int numberOfGrassFields){
        super((int)(sqrt(numberOfGrassFields*10)), (int)(sqrt(numberOfGrassFields*10)));
        int pom = (int)(sqrt(numberOfGrassFields*10));
        grassUpperRightBorder = new Vector2d(pom, pom);
        upperRightBorder = new Vector2d(pom, pom);

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
        return true;
    }

    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();

        if(this.canMoveTo(position)){
            if(isOccupied(position)){
                this.placeGrass();
                mapElements.remove(position);
            }
            animal.setMap(this);
            animal.addObserver(this);
            mapElements.put(animal.getPosition(), animal);
            this.updateBorder(position);
            return true;
        }
        return false;
    }

    private void updateBorder(Vector2d position){
        upperRightBorder = upperRightBorder.upperRight(position);
        lowerLeftBorder = lowerLeftBorder.lowerLeft(position);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if(this.isOccupied(newPosition)){
            this.placeGrass();
            mapElements.remove(newPosition);
        }
        super.positionChanged(oldPosition, newPosition);
        this.updateBorder(newPosition);
    }
}
