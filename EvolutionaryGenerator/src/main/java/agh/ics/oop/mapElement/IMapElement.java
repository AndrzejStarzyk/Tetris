package agh.ics.oop.mapElement;

import agh.ics.oop.Vector2d;

public interface IMapElement {
    Vector2d getPosition();
    int getEnergy();
    Genotype getGenotype();
}
