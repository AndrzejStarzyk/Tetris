package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.tetromino.ITetromino;

import java.util.Iterator;

public interface ITetrominoChangeObserver {
    void monominoesPositionsChanged(Iterator<Vector2d> oldCoordinates, Iterator<Vector2d> newCoordinates);
    void tetrominoStopped(ITetromino tetromino);
}
