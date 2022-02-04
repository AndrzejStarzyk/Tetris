package agh.ics.oop.tetromino;

import agh.ics.oop.Direction;
import agh.ics.oop.Vector2d;
import agh.ics.oop.gui.ITetrominoChangeObserver;

import java.util.List;

public interface ITetromino {
    Vector2d getPosition();
    List<Vector2d> getMonominoesCoordinates();
    void move(Direction direction);
    void rotateClockwise();
    void rotateCounterClockwise();
    void stop();
    void addTetrominoObserver(ITetrominoChangeObserver observer);
    void removeTetrominoObserver(ITetrominoChangeObserver observer);
}
