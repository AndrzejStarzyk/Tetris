package agh.ics.oop.tetromino.pieces;

import agh.ics.oop.Vector2d;
import agh.ics.oop.playfield.Playfield;
import agh.ics.oop.tetromino.AbstractTetromino;
import agh.ics.oop.tetromino.ITetromino;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class I extends AbstractTetromino implements ITetromino {
    public I(Playfield field, Vector2d startingPosition){
        super(new Vector2d[]{new Vector2d(2, 0), new Vector2d(2, -1),
                             new Vector2d(2, -2), new Vector2d(2, -3)}, field, startingPosition);
    }
    @Override
    public void rotateClockwise() {
        List<Vector2d> newCoordinates = monominoesCoordinates.stream()
                .map(Vector2d::rotate90degAround1515).collect(Collectors.toList());
        for (Vector2d coordinates : newCoordinates) {
            if(field.isTileOccupied(position.add(coordinates))){
                return;
            }
        }
        List<Vector2d> oldCoordinates = new LinkedList<>(monominoesCoordinates);
        monominoesCoordinates.clear();
        monominoesCoordinates.addAll(newCoordinates);
        displayChanged(oldCoordinates.stream().map(coordinates -> coordinates.add(position)).collect(Collectors.toList()),
                newCoordinates.stream().map(coordinates -> coordinates.add(position)).collect(Collectors.toList()));
    }

    @Override
    public void rotateCounterClockwise() {
        List<Vector2d> newCoordinates = monominoesCoordinates.stream()
                .map(Vector2d::rotate270degAround1515).collect(Collectors.toList());
        for (Vector2d coordinates : newCoordinates) {
            if(field.isTileOccupied(position.add(coordinates))){
                return;
            }
        }
        List<Vector2d> oldCoordinates = new LinkedList<>(monominoesCoordinates);
        monominoesCoordinates.clear();
        monominoesCoordinates.addAll(newCoordinates);
        displayChanged(oldCoordinates.stream().map(coordinates -> coordinates.add(position)).collect(Collectors.toList()),
                newCoordinates.stream().map(coordinates -> coordinates.add(position)).collect(Collectors.toList()));
    }
}
