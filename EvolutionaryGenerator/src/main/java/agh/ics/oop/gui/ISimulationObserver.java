package agh.ics.oop.gui;

public interface ISimulationObserver {
    void updateStatistics(int day, int nOfAnimals, int nOfGrass, Integer[] dominantGenotype,
                          double averageEnergy, double averageLiveLength, double averageChildren);
}
