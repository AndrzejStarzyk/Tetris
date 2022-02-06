package agh.ics.oop.gui;

import agh.ics.oop.gameplay.GameEngine;
import agh.ics.oop.gameplay.IGameStatusObserver;
import agh.ics.oop.gameplay.PlayerInput;
import agh.ics.oop.playfield.Playfield;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application implements IGameStatusObserver {
    private final Playfield playfield = new Playfield();
    private final VisiblePlayfield visiblePlayfield = new VisiblePlayfield(playfield);
    private final Statistics statistics = new Statistics(playfield);
    private final GameEngine gameEngine = new GameEngine(playfield, statistics);
    private final PlayerInput playerInput = new PlayerInput(playfield, gameEngine);

    private final Thread gameThread = new Thread(gameEngine);
    private long startTime;
    private Stage primaryStage;

    @Override
    public void init() {
        playfield.addGameStatusObserver(this);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Text controls = new Text("Controls:");
        Text movementControls = new Text("Press left and right arrow keys to move tetromino horizontally");
        Text rotationControls = new Text("Press 'z' and 'x' keys to rotate tetromino");
        Text speedUpControls = new Text("Press down arrow key to increase tetromino falling speed");
        Text statisticsControls = new Text(
                "After exiting game statistics will be saved in file in the statistics.txt file in the application directory");

        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button resumeButton = new Button("Resume");
        Button exitButton = new Button("Exit");

        Scene scene = new Scene(
                new VBox(
                        new HBox(startButton, stopButton, resumeButton, exitButton),
                        new HBox(visiblePlayfield.getGridPane(),
                                new VBox(
                                        controls,
                                    movementControls,
                                    speedUpControls,
                                    rotationControls,
                                    statisticsControls,
                                    statistics.getIngameStatistics()))
                ), 1000, 800);
        scene.addEventFilter(KeyEvent.ANY, playerInput);

        startButton.setOnAction(event -> {
            startTime = System.currentTimeMillis();
            gameThread.start();
            startButton.setDisable(true);
        });
        stopButton.setOnAction(event -> {
            synchronized (gameEngine){
                gameThread.interrupt();
            }
            scene.removeEventFilter(KeyEvent.ANY, playerInput);
            stopButton.setDisable(true);
            resumeButton.setDisable(false);
        });
        resumeButton.setOnAction(event -> {
            scene.addEventFilter(KeyEvent.ANY, playerInput);
            synchronized (gameEngine){
                gameEngine.notify();
            }
            stopButton.setDisable(false);
            resumeButton.setDisable(true);
        });
        resumeButton.setDisable(true);
        exitButton.setOnAction(event -> gameOver());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }


    @Override
    public void gameOver() {
        Platform.runLater(() -> {
            Button exitButton = new Button("Exit");
            exitButton.setOnAction(event -> Platform.exit());
            this.primaryStage.setScene(new Scene(new VBox(
                    exitButton,
                    statistics.createPostgameStatistics(startTime)), 400 ,400));
        });
    }

    @Override
    public void nextTetromino() {

    }
}
