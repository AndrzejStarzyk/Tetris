package agh.ics.oop.playfield;

import agh.ics.oop.Vector2d;
import agh.ics.oop.gameplay.IGameStatusObserver;
import agh.ics.oop.gui.IPlayfieldChangeObserver;
import agh.ics.oop.gui.ITetrominoChangeObserver;
import agh.ics.oop.tetromino.ITetromino;

import java.util.*;
import java.util.stream.Collectors;

public class Playfield implements  ITetrominoChangeObserver {
    private final int height = 20;
    private final int width = 10;
    private final Vector2d lowerLeft = new Vector2d(0,0);
    private final Vector2d visibleUpperRight = new Vector2d(width-1, height-1);
    private final Vector2d invisibleUpperRight = new Vector2d(width-1, 2*height-1);
    private final Vector2d startingPosition = new Vector2d(3, height+3);

    private final List<IPlayfieldChangeObserver> playfieldObservers = new LinkedList<>();
    private final List<IGameStatusObserver> gameStatusObservers = new LinkedList<>();

    private final Random random = new Random();
    private final List<Line> lines = new LinkedList<>();
    private ITetromino currentTetromino;

    public Playfield() {}

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Vector2d getStartingPosition() {
        return startingPosition;
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
        if (successfullySpawned) {
            for (IPlayfieldChangeObserver observer : playfieldObservers) {
                observer.tetrominoSpawned(tetromino);
            }
        }
    }

    @Override
    public void tetrominoStopped(ITetromino tetromino) {
        currentTetromino.removeTetrominoObserver(this);
        List<Integer> fullLines = new LinkedList<>();
        for (Vector2d localCoordinates : currentTetromino.getMonominoesCoordinates()) {
            Vector2d coordinates = currentTetromino.getPosition().add(localCoordinates);
            int missingLines = coordinates.y + 1 - lines.size();
            if(missingLines > 0) {
                for (int i = 0; i < missingLines; i++) {
                    lines.add(new Line());
                }
            }
            lines.get(coordinates.y).placeMonomino(coordinates.x);
            if(lines.get(coordinates.y).isFull()) {
                fullLines.add(coordinates.y);
            }
        }
        fullLines.sort(Comparator.reverseOrder());
        for (int lineNr : fullLines) {
            lines.remove(lines.get(lineNr));
        }
        if(!fullLines.isEmpty()){
            for (IPlayfieldChangeObserver observer : playfieldObservers) {
                observer.linesRemoved(fullLines.stream().sorted().map(this::lineNrFromTop).collect(Collectors.toList()));
            }
        }

        for (IGameStatusObserver observer : gameStatusObservers) {
            observer.nextTetromino();
        }
    }

    public boolean isTileOccupied(Vector2d position){
        if(!(position.follows(lowerLeft) && position.precedes(invisibleUpperRight))) {
            return true;
        }
        if(position.y < lines.size()) {
            return lines.get(position.y).isTileOccupied(position.x);
        }
        return false;
    }
    public boolean isTileVisible(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(visibleUpperRight);
    }

    public void addPlayfieldObserver(IPlayfieldChangeObserver observer){
        playfieldObservers.add(observer);
    }
    public void addGameStatusObserver(IGameStatusObserver observer){
        gameStatusObservers.add(observer);
    }

    @Override
    public void monominoesPositionsChanged(List<Vector2d> oldCoordinates, List<Vector2d> newCoordinates) {}
}
