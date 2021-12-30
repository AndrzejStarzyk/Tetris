package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{
    private MoveDirection[] directions;
    private final IWorldMap map;
    private final Vector2d[] positions;
    private final List<Animal> animals = new ArrayList<>();
    private List<IPositionChangeObserver> observers = new LinkedList<>();
    public final int moveDelay = 2000;

    public void setDirections(MoveDirection[] directions){
        this.directions = directions;
    }
    public  SimulationEngine(IWorldMap map, Vector2d[] positions){
        this.map = map;
        this.positions = positions;
        for(Vector2d position : positions){
            animals.add(new Animal(map, position, MapDirection.NORTH));
        }
    }
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
            try {
                Thread.sleep(moveDelay);
            }
            catch (InterruptedException interruptedException){
                System.out.println(interruptedException.getMessage());
                System.exit(0);
            }
            currentAnimal = animals.get(i);
            Vector2d oldPosition = currentAnimal.getPosition();
            currentAnimal.move(direction);
            Vector2d newPosition = currentAnimal.getPosition();

            positionChanged(oldPosition, newPosition);
            i = (i+1) % animals.size();
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
}
