package agh.ics.oop.playfield;

import agh.ics.oop.Vector2d;
import agh.ics.oop.gameplay.IGameStatusObserver;
import agh.ics.oop.gui.IPlayfieldChangeObserver;
import agh.ics.oop.gui.ITetrominoChangeObserver;
import agh.ics.oop.tetromino.ITetromino;

import java.util.*;
import java.util.stream.Collectors;

public class Playfield implements  ITetrominoChangeObserver{
    private final int height = 20;
    private final int width = 10;
    private final Vector2d lowerLeft = new Vector2d(0,0);
    private final Vector2d upperRight = new Vector2d(width-1, height-1);
    private final Vector2d startingPosition = new Vector2d(3, height-1);

    private final List<IPlayfieldChangeObserver> playfieldObservers = new LinkedList<>();
    private final List<IGameStatusObserver> gameStatusObservers = new LinkedList<>();

    private final Random random = new Random();
    private final List<Line> lines = new LinkedList<>();
    private ITetromino currentTetromino;

    public Playfield() {

    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Vector2d getStartingPosition() {
        return startingPosition;
    }

    public ITetromino getCurrentTetromino() {
        return currentTetromino;
    }

    private int lineNrFromTop(int lineNrFromBottom) {
        return height - 1 - lineNrFromBottom;
    }

    public void spawnTetromino(ITetromino tetromino) {
        boolean successfullySpawned = true;
        currentTetromino = tetromino;
        currentTetromino.addTetrominoObserver(this);
        for (int i = 0; i < random.nextInt(4); i++) {
            currentTetromino.rotateClockwise();
        }
        for (Vector2d coordinates : currentTetromino.getMonominoesCoordinates()) {
            if(isTileOccupied(currentTetromino.getPosition().add(coordinates))) {
                successfullySpawned = false;
                for (IGameStatusObserver observer : gameStatusObservers) {
                    observer.gameOver();
                }
                break;
            }
        }
        if (successfullySpawned){
            for (IPlayfieldChangeObserver observer : playfieldObservers) {
                observer.tetrominoSpawned(tetromino);
            }
        }
    }

    @Override
    public void tetrominoStopped(ITetromino tetromino) {
        System.out.println("playfield notified tetromino stopping");
        currentTetromino.removeTetrominoObserver(this);
        List<Integer> fullLines = new LinkedList<>();
        for (Vector2d localCoordinates : currentTetromino.getMonominoesCoordinates()) {
            Vector2d coordinates = currentTetromino.getPosition().add(localCoordinates);
            int missingLines = coordinates.y + 1 - lines.size();
            if(missingLines > 0){
                for (int i = 0; i < missingLines; i++) {
                    lines.add(new Line());
                }
            }
            lines.get(coordinates.y).placeMonomino(coordinates.x);
            if(lines.get(coordinates.y).isFull()) {
                fullLines.add(coordinates.y);
            }
        }
        for (int lineNr : fullLines) {
            lines.remove(lines.get(lineNr));
        }
        if(!fullLines.isEmpty()){
            System.out.println("removing full lines");
            for (IPlayfieldChangeObserver observer : playfieldObservers) {
                observer.linesRemoved(fullLines.stream().sorted().map(this::lineNrFromTop).collect(Collectors.toList()));
            }
        }

        for (IGameStatusObserver observer : gameStatusObservers) {
            observer.nextTetromino();
        }
        System.out.println("playfield ended stopping");
    }

    public boolean isTileOccupied(Vector2d position){
        if(!(position.follows(lowerLeft) && position.precedes(upperRight))) {
            return true;
        }
        if(position.y < lines.size()) {
            return lines.get(position.y).isTileOccupied(position.x);
        }
        return false;
    }

    public void addPlayfieldObserver(IPlayfieldChangeObserver observer){
        playfieldObservers.add(observer);
    }
    public void removePlayfieldObserver(IPlayfieldChangeObserver observer){
        playfieldObservers.remove(observer);
    }
    public void addGameStatusObserver(IGameStatusObserver observer){
        gameStatusObservers.add(observer);
    }
    public void removeGameStatusObserver(IGameStatusObserver observer){
        gameStatusObservers.remove(observer);
    }

    @Override
    public void monominoesPositionsChanged(Iterator<Vector2d> oldCoordinates, Iterator<Vector2d> newCoordinates) {

    }
}
