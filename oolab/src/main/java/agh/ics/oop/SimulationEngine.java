package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{
    private final MoveDirection[] directions;
    private final IWorldMap map;
    private final Vector2d[] positions;
    private final List<Animal> animals = new ArrayList<>();

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] positions){
        this.directions = directions;
        this.map = map;
        this.positions = positions;
        for(Vector2d position : positions){
            animals.add(new Animal(map, position, MapDirection.NORTH));
        }
    }

    public void run() {
        Animal currentAnimal;
        int i=0;
        for(MoveDirection direction : directions){
            currentAnimal = animals.get(i);
            currentAnimal.move(direction);
            i = (i+1) % positions.length;
        }
    }
}
