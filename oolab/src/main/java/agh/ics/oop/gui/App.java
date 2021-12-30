package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;

public class App extends Application implements IPositionChangeObserver{
    private AbstractWorldMap map;
    private final GridPane gridPane = new GridPane();
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private final Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
    private SimulationEngine engine;
    private double width;
    private double height;

    public void init(){
        map = new GrassField(10);
        lowerLeft = map.getLowerLeft();
        upperRight = map.getUpperRight();
        width = (int)(400/((double)upperRight.x-(double)lowerLeft.x+2));
        height = (int)(400/((double)upperRight.y-(double)lowerLeft.y+2));
        engine = new SimulationEngine(map, positions);
        engine.addObserver(this);
    }

    public void start(Stage primaryStage){
        gridPane.setGridLinesVisible(true);
        for (int i = lowerLeft.x - 1; i <= upperRight.x; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
        }
        for (int i = lowerLeft.y - 1; i <= upperRight.y; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(height));
        }
        Label xy = new Label("x/y");
        gridPane.add(xy, 0, 0);
        GridPane.setHalignment(xy, HPos.CENTER);

        addElements();

        TextField textField = new TextField();
        Button buttonStart = new Button("Start");

        buttonStart.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                engine.setDirections(OptionsParser.parseDirections(textField.getText()));
                Thread thread = new Thread(engine);
                thread.start();
            }
        });

        HBox input = new HBox(textField, buttonStart);
        VBox container = new VBox(input, gridPane);

        Scene scene = new Scene(container, 400, 440);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addElements(){
        if(lowerLeft.follows(map.getLowerLeft()) && !lowerLeft.equals(map.getLowerLeft())){
            gridPane.getColumnConstraints().add(new ColumnConstraints(40));
            lowerLeft = map.getLowerLeft();
        }
        if(upperRight.precedes(map.getUpperRight()) && !upperRight.equals(map.getUpperRight())){
            gridPane.getRowConstraints().add(new RowConstraints(40));
            upperRight = map.getUpperRight();
        }

        Label label;
        VBox elementBox;

        for (int i = lowerLeft.x; i <= upperRight.x; i++) {
            label = new Label(Integer.toString(i));
            gridPane.add(label, i-lowerLeft.x+1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = lowerLeft.y; i <= upperRight.y; i++) {
            label = new Label(Integer.toString(i));
            gridPane.add(label, 0, upperRight.y-i+1);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for(Vector2d position :map.getMapElements().keySet()){
            elementBox = new GuiElementBox(map.getMapElements().get(position)).vBox;
            System.out.println(gridPane.getColumnConstraints().get(0).getMinWidth());
            gridPane.add(elementBox, position.x-lowerLeft.x+1, upperRight.y-position.y+1);
            GridPane.setHalignment(elementBox, HPos.CENTER);
        }
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Platform.runLater(()-> {
            Node node = gridPane.getChildren().get(0);
            gridPane.getChildren().clear();
            gridPane.add(node, 0, 0);
            GridPane.setHalignment(node, HPos.CENTER);
            addElements();
        });
    }
}