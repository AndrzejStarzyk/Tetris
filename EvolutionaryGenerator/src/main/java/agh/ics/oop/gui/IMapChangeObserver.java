package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.mapElement.Animal;

public interface IMapChangeObserver {
    void animalRemoved(Animal animal);
    void animalAdded(Animal animal);
    void animalDied(Animal animal);
    void grassRemoved(Vector2d position);
    void grassAdded(Vector2d position);
}
