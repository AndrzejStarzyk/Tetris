package agh.ics.oop.gui;

import agh.ics.oop.playfield.Playfield;
import agh.ics.oop.tetromino.ITetromino;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class Statistics implements IPlayfieldChangeObserver{
    private int points = 0;
    private int lines = 0;
    private int tetrises = 0;
    private int level = 0;

    private final Text pointsText = new Text("");
    private final Text linesText = new Text("");
    private final Text tetrisesText = new Text("");
    private final Text levelText = new Text("");

    private final VBox vbox;

    public Statistics(Playfield playfield) {
        playfield.addPlayfieldObserver(this);
        this.vbox = new VBox(pointsText, linesText, tetrisesText, levelText);
    }

    public VBox getVbox() {
        return vbox;
    }

    @Override
    public void tetrominoSpawned(ITetromino tetromino) {

    }

    @Override
    public void linesRemoved(List<Integer> lines) {
        this.lines += lines.size();
        int pointsGained = switch (lines.size()){
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

    public void extraPoints(){
        points += 2;
        level = Math.floorDiv(points, 200);
        Platform.runLater(()->{
            pointsText.setText(String.valueOf(points));
            levelText.setText(String.valueOf(level));
        });
    }
}
