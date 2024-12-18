package agh.ics.oop.tetromino;

import agh.ics.oop.Direction;
import agh.ics.oop.Vector2d;
import agh.ics.oop.gui.ITetrominoChangeObserver;
import agh.ics.oop.playfield.Playfield;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractTetromino implements ITetromino {
    protected final List<Vector2d> monominoesCoordinates = new LinkedList<>();
    protected final Playfield field;
    protected final Vector2d startingPosition;
    protected Vector2d position;
    private final List<ITetrominoChangeObserver> observers = new LinkedList<>();

    protected final static Vector2d usualCenter = new Vector2d(1, -1);

    protected AbstractTetromino(Vector2d[] monominoesCoordinates, Playfield field, Vector2d startingPosition) {
        this.monominoesCoordinates.addAll(Arrays.asList(monominoesCoordinates));
        this.field = field;
        this.startingPosition = startingPosition;
        this.position = startingPosition;
    }

    public Vector2d getPosition() {
        return position;
    }

    public List<Vector2d> getMonominoesCoordinates() {
        return monominoesCoordinates;
    }

    @Override
    public void move(Direction direction) {
        boolean shouldStop = false;
        for (Vector2d coordinates : monominoesCoordinates) {
            if(field.isTileOccupied(position.add(coordinates).add(direction.getDirection()))) {
                shouldStop = true;
                break;
            }
        }
        if(shouldStop) {
            if(direction.equals(Direction.Down)) {
                stop();
            }
        }
        else {
            Vector2d oldPosition = position;
            position = position.add(direction.getDirection());
            displayChanged(monominoesCoordinates.stream()
                            .map(coordinates -> coordinates.add(oldPosition)).collect(Collectors.toList()),
                    monominoesCoordinates.stream()
                            .map(coordinates -> coordinates.add(position)).collect(Collectors.toList()));
        }
    }

    @Override
    public void rotateClockwise() {
        List<Vector2d> newCoordinates = monominoesCoordinates.stream()
                .map(coordinates -> coordinates.rotate90degAround(usualCenter))
                .collect(Collectors.toList());
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
                .map( coordinates -> coordinates.rotate270degAround(usualCenter))
                .collect(Collectors.toList());
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
    public void stop() {
        for (ITetrominoChangeObserver observer : observers) {
            observer.tetrominoStopped(this);
        }
        observers.clear();
        position = startingPosition;
    }

    protected void displayChanged(List<Vector2d> oldCoordinates, List<Vector2d> newCoordinates) {
        for (ITetrominoChangeObserver  observer: observers) {
            observer.monominoesPositionsChanged(oldCoordinates, newCoordinates);
        }
    }
    @Override
    public void addTetrominoObserver(ITetrominoChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeTetrominoObserver(ITetrominoChangeObserver observer) {
        observers.remove(observer);
    }
}
