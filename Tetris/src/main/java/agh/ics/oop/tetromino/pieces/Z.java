package agh.ics.oop.tetromino.pieces;

import agh.ics.oop.Vector2d;
import agh.ics.oop.playfield.Playfield;
import agh.ics.oop.tetromino.AbstractTetromino;
import agh.ics.oop.tetromino.ITetromino;

public class Z extends AbstractTetromino implements ITetromino {
    public Z(Playfield field, Vector2d startingPosition) {
        super(new Vector2d[]{new Vector2d(0, -2), new Vector2d(1, -1),
                             new Vector2d(1, -2), new Vector2d(2, -1)}, field, startingPosition);
    }
}
