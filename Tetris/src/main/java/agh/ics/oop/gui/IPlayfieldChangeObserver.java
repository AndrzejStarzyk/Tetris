package agh.ics.oop.gui;

import agh.ics.oop.tetromino.ITetromino;

import java.util.List;

public interface IPlayfieldChangeObserver {
    void tetrominoSpawned(ITetromino tetromino);
    void linesRemoved(List<Integer> lines);
}
