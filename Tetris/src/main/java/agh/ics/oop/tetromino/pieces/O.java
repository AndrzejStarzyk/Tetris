package agh.ics.oop.tetromino.pieces;

import agh.ics.oop.Vector2d;
import agh.ics.oop.playfield.Playfield;
import agh.ics.oop.tetromino.AbstractTetromino;
import agh.ics.oop.tetromino.ITetromino;

public class O extends AbstractTetromino implements ITetromino {

    public O(Playfield field, Vector2d startingPosition) {
        super(new Vector2d[]{new Vector2d(0, 0), new Vector2d(1, 0),
                             new Vector2d(0, -1), new Vector2d(1, -1)}, field, startingPosition);
    }

    @Override
    public void rotateClockwise() {
    }

    @Override
    public void rotateCounterClockwise() {
    }
}
