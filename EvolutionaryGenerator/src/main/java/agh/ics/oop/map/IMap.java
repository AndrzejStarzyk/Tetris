package agh.ics.oop.map;

import agh.ics.oop.Vector2d;
import agh.ics.oop.gui.IDisplayChangeObserver;
import agh.ics.oop.gui.IMapChangeObserver;
import agh.ics.oop.mapElement.*;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

public interface IMap {
    Vector2d NewPosition(Vector2d oldPosition, Vector2d r);
    void placeAnimal(Animal animal);
    void removeAnimal(Animal animal);
    void placeGrass();
    void removeGrass(Vector2d position);

    boolean inJungle(Vector2d position);
    boolean noAnimal(Vector2d position);
    boolean isGrassOn(Vector2d position);
    int maxEnergy(Vector2d position);
    int nOfStrongest(Vector2d position);

    Vector2d getUpperRight();
    HashMap<Vector2d, PriorityQueue<Animal>> getAnimals();
    Set<Vector2d> getGrass();

    void addMapObserver(IMapChangeObserver observer);
    void removeMapObserver(IMapChangeObserver observer);
    void addDisplayObserver(IDisplayChangeObserver observer);
    void removeDisplayObserver(IDisplayChangeObserver observer);
}
