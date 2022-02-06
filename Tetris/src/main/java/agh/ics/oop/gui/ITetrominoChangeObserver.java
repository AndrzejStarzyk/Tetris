package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.tetromino.ITetromino;

import java.util.List;

public interface ITetrominoChangeObserver {
    void monominoesPositionsChanged(List<Vector2d> oldCoordinates, List<Vector2d> newCoordinates);
    void tetrominoStopped(ITetromino tetromino);
}
