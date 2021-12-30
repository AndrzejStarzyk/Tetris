package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;

public interface IDisplayChangeObserver {
    void grassRemoved(Vector2d position);
    void grassAdded(Vector2d position);
    void animalLeft(Vector2d position, boolean allGone, int newMaxEnergy);
    void animalArrived(Vector2d position, int energy);
    void updateMaxEnergy(Vector2d position, int energy);

}
