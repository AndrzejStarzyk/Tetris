package agh.ics.oop;

import agh.ics.oop.mapElement.Animal;

public interface IAnimalChangeObserver {
    void energyChanged(Animal animal, int oldEnergy);
    void positionChanged(Animal animal, Vector2d oldPosition);
    void animalDied(Animal animal);
    void newChild(int children);
    void newDescendant(int descendants);
}
