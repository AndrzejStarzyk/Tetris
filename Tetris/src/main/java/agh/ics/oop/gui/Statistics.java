package agh.ics.oop.gui;

import agh.ics.oop.playfield.Playfield;
import agh.ics.oop.tetromino.ITetromino;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class Statistics implements IPlayfieldChangeObserver {
    private int points = 0;
    private int lines = 0;
    private int tetrises = 0;
    private int level = 0;

    private final Text pointsText = new Text("0");
    private final Text linesText = new Text("0");
    private final Text tetrisesText = new Text("0");
    private final Text levelText = new Text("0");


    private final Label pointsLabel = new Label("Points:   ");
    private final Label linesLabel = new Label("Lines Removed:   ");
    private final Label tetrisesLabel = new Label("Tetris score:   ");
    private final Label levelLabel = new Label("Current Level:   ");

    private final VBox ingameStatistics;

    public Statistics(Playfield playfield) {
        playfield.addPlayfieldObserver(this);
        this.ingameStatistics = new VBox(
                new HBox(pointsLabel, pointsText),
                new HBox(linesLabel, linesText),
                new HBox(tetrisesLabel, tetrisesText),
                new HBox(levelLabel, levelText));
    }

    public VBox getIngameStatistics() {
        return ingameStatistics;
    }

    @Override
    public void tetrominoSpawned(ITetromino tetromino) {

    }

    @Override
    public void linesRemoved(List<Integer> lines) {
        this.lines += lines.size();
        int pointsGained = switch (lines.size()) {
            case 1 -> 10;
            case 2 -> 20;
            case 3 -> 30;
            case 4 -> 40;
            default -> 0;
        };
        points += pointsGained;
        if(lines.size() == 4) tetrises++;
        level = Math.floorDiv(points, 200);
        Platform.runLater(()->{
            pointsText.setText(String.valueOf(points));
            linesText.setText(String.valueOf(this.lines));
            tetrisesText.setText(String.valueOf(tetrises));
            levelText.setText(String.valueOf(level));
        });
    }

    public void extraPoints() {
        points += 2;
        level = Math.floorDiv(points, 200);
        Platform.runLater(() -> {
            pointsText.setText(String.valueOf(points));
            levelText.setText(String.valueOf(level));
        });
    }
    public VBox createPostgameStatistics(Long startTime) {
        long gameLength =  Math.round((System.currentTimeMillis() - (double)startTime)/1000);
        try {
            Path statisticsPath = Paths.get(
                    System.getProperty("user.dir")+System.getProperty("file.separator")+"statistics.txt");
            if(!Files.exists(statisticsPath)){
                Files.createFile(statisticsPath);
            }
            String statistics = new String("");
            for (int number : Arrays.asList(points, lines, tetrises, level)) {
                statistics = statistics.concat(String.valueOf(number)).concat(",");
            }
            statistics = statistics.concat(String.valueOf(gameLength));
            Files.write(statisticsPath, List.of(statistics), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Label timeLabel = new Label("Game length:   ");
        Text timeText = new Text(String.valueOf(gameLength));
        return new VBox(
                new HBox(pointsLabel, pointsText),
                new HBox(linesLabel, linesText),
                new HBox(tetrisesLabel, tetrisesText),
                new HBox(levelLabel, levelText),
                new HBox(timeLabel, timeText));

    }
}
