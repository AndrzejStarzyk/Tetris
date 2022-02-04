package agh.ics.oop.gameplay;

import agh.ics.oop.Direction;
import agh.ics.oop.Vector2d;
import agh.ics.oop.gui.Statistics;
import agh.ics.oop.playfield.Playfield;
import agh.ics.oop.tetromino.ITetromino;
import agh.ics.oop.tetromino.pieces.*;

import javafx.application.Platform;

import java.util.*;

public class GameEngine implements Runnable, IGameStatusObserver{
    private final Playfield playfield;
    private final Statistics statistics;

    private final List<ITetromino> tetrominoes = new ArrayList<>();
    private ITetromino currentTetromino;

    private boolean gameOver = false;
    private boolean tetrominoCanMove = true;
    private int moveDelay = 3000;

    private final int slow = 3000;
    private final int fast = 100;
    private final Random random = new Random();

    public GameEngine(Playfield playfield, Statistics statistics) {
        this.playfield = playfield;
        this.statistics = statistics;

        playfield.addGameStatusObserver(this);

        Vector2d startingPosition = playfield.getStartingPosition();
        tetrominoes.addAll(Arrays.asList(
                new I(playfield, startingPosition),
                new J(playfield, startingPosition),
                new L(playfield, startingPosition),
                new O(playfield, startingPosition),
                new S(playfield, startingPosition),
                new Z(playfield, startingPosition),
                new T(playfield, startingPosition)));
    }

    @Override
    public void run() {
        while(!gameOver){
            checkForInterruption();
            spawnTetromino();
            checkForInterruption();
            tetrominoCanMove = true;
            while (tetrominoCanMove){
                try {
                    System.out.println(moveDelay);
                    if(moveDelay == slow){
                        for (int i = 0; i < 10; i++) {
                            Thread.sleep(moveDelay/10);
                            if(moveDelay!= slow){
                                break;
                            }
                        }
                    }
                    else {
                        Thread.sleep(moveDelay);
                    }

                    checkForInterruption();
                    moveTetromino();
                    checkForInterruption();
                    if(moveDelay == fast){
                        statistics.extraPoints();
                    }
                } catch (InterruptedException e) {
                    synchronized (this){
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Platform.exit();
    }

    private void spawnTetromino(){
        currentTetromino = tetrominoes.get(random.nextInt(7));
        synchronized (this){
            playfield.spawnTetromino(currentTetromino);
        }

    }

    private void moveTetromino(){
        synchronized (this){
            currentTetromino.move(Direction.Down);
        }

    }

    @Override
    public void gameOver() {
        gameOver = true;
    }

    @Override
    public void nextTetromino() {
        tetrominoCanMove = false;
    }

    public void speedUpFalling(){
        moveDelay = fast;
    }
    public void slowDownFalling(){
        moveDelay = slow;
    }
    private void checkForInterruption(){
        synchronized (this){
            while (Thread.currentThread().isInterrupted()){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
