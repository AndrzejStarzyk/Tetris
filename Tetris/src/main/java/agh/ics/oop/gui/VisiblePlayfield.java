package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.playfield.Playfield;
import agh.ics.oop.tetromino.ITetromino;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.*;

public class VisiblePlayfield implements ITetrominoChangeObserver, IPlayfieldChangeObserver {
    private final Playfield playfield;
    private final GridPane gridPane = new GridPane();
    private final Pane[][] panes;
    private final int width;
    private final int height;
    private Background currentTetrominoBackground;

    private final Background empty = new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY));
    private final List<Background> cellsBackgrounds = new ArrayList<Background>(Arrays.asList(
            new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)),
            new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)),
            new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY))));

    private final Random random = new Random();

    public VisiblePlayfield(Playfield playfield) {
        this.playfield = playfield;
        playfield.addPlayfieldObserver(this);
        width = playfield.getWidth();
        height = playfield.getHeight();
        panes = new Pane[width][height];

        double cellSize = 30;
        for (int i = 0; i < height; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(cellSize +2));
        }
        for (int i = 0; i < width; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellSize +2));
        }
        Border cellBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pane pane = new Pane();
                pane.setBackground(empty);
                pane.setBorder(cellBorder);
                panes[i][j] = pane;
                gridPane.add(pane, i, j);
            }
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    private int lineNrFromTop(int lineNrFromBottom) {
        return height - 1 - lineNrFromBottom;
    }

    @Override
    public void monominoesPositionsChanged(Iterator<Vector2d> oldCoordinates, Iterator<Vector2d> newCoordinates) {
        Platform.runLater(() -> {
            System.out.println("changing display of monominoes");
            while (oldCoordinates.hasNext()) {
                Vector2d coordinates = oldCoordinates.next();
                panes[coordinates.x][lineNrFromTop(coordinates.y)].setBackground(empty);
            }
            while (newCoordinates.hasNext()){
                Vector2d coordinates = newCoordinates.next();
                panes[coordinates.x][lineNrFromTop(coordinates.y)].setBackground(currentTetrominoBackground);
            }
            System.out.println("changed display of monominoes");
        });

    }

    @Override
    public void tetrominoSpawned(ITetromino tetromino) {
        Platform.runLater(() -> {
            System.out.println("visualizing new");
            tetromino.addTetrominoObserver(this);
            currentTetrominoBackground = cellsBackgrounds.get(random.nextInt(cellsBackgrounds.size()));
            Iterator<Vector2d> monominoesCoordinates = tetromino.getMonominoesCoordinates().
                    stream().map(coordinates -> coordinates.add(tetromino.getPosition())).iterator();
            while (monominoesCoordinates.hasNext()){
                Vector2d coordinates = monominoesCoordinates.next();
                panes[coordinates.x][lineNrFromTop(coordinates.y)].setBackground(currentTetrominoBackground);
            }
            System.out.println("visualized new");
        });

    }

    @Override
    public void tetrominoStopped(ITetromino tetromino) {
        System.out.println("visualizer notified about stopping");
        tetromino.removeTetrominoObserver(this);
        System.out.println("visualizer unsubscribed");
    }

    @Override
    public void linesRemoved(List<Integer> lines) {
        Platform.runLater(()->{
            System.out.println("removing lines");
            int dropHeight = 0;
            for (int currentLine = lines.get(0); currentLine > -1; currentLine--) {
                if(!lines.isEmpty() && currentLine == lines.get(0)){
                    lines.remove(0);
                    dropHeight++;
                }
                int lineToPlaceHere = currentLine - dropHeight;
                for (int lineNr : lines) {
                    if(lineNr < currentLine && lineNr >= lineToPlaceHere){
                        lineToPlaceHere--;
                    }
                }
                System.out.println(currentLine);
                System.out.println(lineToPlaceHere);
                if(lineToPlaceHere >= 0){
                    for (int cellX = 0; cellX < width; cellX++) {
                        panes[cellX][currentLine].setBackground(panes[cellX][lineToPlaceHere].getBackground());
                    }
                }
                else{
                    for (int cellX = 0; cellX < width; cellX++) {
                        panes[cellX][currentLine].setBackground(empty);
                    }
                }

            }
            System.out.println("visualizer removed lines");
        });

    }
}
