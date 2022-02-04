package agh.ics.oop.gui;

import agh.ics.oop.gameplay.GameEngine;
import agh.ics.oop.gameplay.PlayerInput;
import agh.ics.oop.playfield.Playfield;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;


public class App extends Application{
    private final Playfield playfield = new Playfield();
    private final VisiblePlayfield visiblePlayfield = new VisiblePlayfield(playfield);
    private final Statistics statistics = new Statistics(playfield);
    private final GameEngine gameEngine = new GameEngine(playfield, statistics);
    private final PlayerInput playerInput = new PlayerInput(playfield, gameEngine);
    private Thread gameThread;



    @Override
    public void start(Stage primaryStage) {
        gameThread = new Thread(gameEngine);

        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button resumeButton = new Button("Resume");
        Button exitButton = new Button("Exit");

        Scene scene = new Scene(
                new VBox(
                        new HBox(startButton, stopButton, resumeButton, exitButton),
                        new HBox(visiblePlayfield.getGridPane(), statistics.getVbox())
                ), 400, 800);
        scene.addEventFilter(KeyEvent.ANY, playerInput);

        startButton.setOnAction(event -> gameThread.start());
        stopButton.setOnAction(event -> {
            synchronized (gameEngine){
                gameThread.interrupt();
            }
            scene.removeEventFilter(KeyEvent.ANY, playerInput);
        });
        resumeButton.setOnAction(event -> {
            scene.addEventFilter(KeyEvent.ANY, playerInput);
            synchronized (gameEngine){
                gameEngine.notify();
            }
        });
        exitButton.setOnAction(event -> Platform.exit());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }


}
