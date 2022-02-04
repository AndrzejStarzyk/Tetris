package agh.ics.oop.gameplay;

import agh.ics.oop.Direction;
import agh.ics.oop.gui.IPlayfieldChangeObserver;
import agh.ics.oop.playfield.Playfield;
import agh.ics.oop.tetromino.ITetromino;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class PlayerInput implements EventHandler<KeyEvent>, IPlayfieldChangeObserver, IGameStatusObserver {
    private ITetromino tetromino;
    private boolean canTetrominoMove;
    private final GameEngine gameEngine;
    public PlayerInput(Playfield playfield, GameEngine gameEngine) {
        playfield.addPlayfieldObserver(this);
        playfield.addGameStatusObserver(this);
        this.gameEngine = gameEngine;
    }

    @Override
    public void handle(KeyEvent event) {
        synchronized (gameEngine){
            if(canTetrominoMove){
                if(event.getCode().equals(KeyCode.RIGHT) && event.getEventType().equals(KeyEvent.KEY_PRESSED)){
                    tetromino.move(Direction.Right);
                }
                if (event.getCode().equals(KeyCode.LEFT) && event.getEventType().equals(KeyEvent.KEY_PRESSED)){
                    tetromino.move(Direction.Left);
                }
                if (event.getCode().equals(KeyCode.X) && event.getEventType().equals(KeyEvent.KEY_PRESSED)){
                    tetromino.rotateClockwise();
                }
                if (event.getCode().equals(KeyCode.Z) && event.getEventType().equals(KeyEvent.KEY_PRESSED)){
                    tetromino.rotateCounterClockwise();
                }
                if (event.getCode().equals(KeyCode.DOWN) && event.getEventType().equals(KeyEvent.KEY_PRESSED)){
                    gameEngine.speedUpFalling();
                }
                if (event.getCode().equals(KeyCode.DOWN) && event.getEventType().equals(KeyEvent.KEY_RELEASED)){
                    gameEngine.slowDownFalling();
                }
            }

        }
    }

    @Override
    public void tetrominoSpawned(ITetromino tetromino) {
        System.out.println("can move");
        this.tetromino = tetromino;
        canTetrominoMove = true;
    }

    @Override
    public void linesRemoved(List<Integer> lines) {

    }

    @Override
    public void gameOver() {

    }

    @Override
    public void nextTetromino() {
        System.out.println("cannot move");
        canTetrominoMove = false;
    }
}
