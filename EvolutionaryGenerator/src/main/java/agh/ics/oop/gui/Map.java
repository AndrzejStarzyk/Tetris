package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.map.IMap;
import agh.ics.oop.mapElement.Animal;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class Map implements IDisplayChangeObserver{
    private final IMap map;
    private final Statistics statistics;
    private final SimulationEngine engine;

    private final VBox vBox;
    private final GridPane gridPane = new GridPane();
    private final Pane[][] panes;
    private final HashMap<Vector2d, Circle> circles = new HashMap<>();
    private final int fieldsX;
    private final int fieldsY;
    private final double size;
    private final int startEnergy = App.getStartEnergy();
    private final HashMap<Vector2d, Paint> toRestore = new HashMap<>();

    private final Background jungle = new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background savanna = new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background grass = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    private final Color noEnergy = Color.BLACK;
    private final Color veryLowEnergy = Color.valueOf("#696969");
    private final Color lowEnergy = Color.valueOf("#A9A9A9");
    private final Color mediumEnergy = Color.valueOf("#D2B48C");
    private final Color highEnergy = Color.valueOf("#FFDEAD");
    private final Color veryHighEnergy = Color.valueOf("#FFE4C4");
    private final Color highlight = Color.RED;

    public Map(IMap map, Statistics statistics, SimulationEngine engine){
        this.statistics = statistics;
        this.engine = engine;
        this.map  = map;
        map.addDisplayObserver(this);

        fieldsX = map.getUpperRight().x + 1;
        fieldsY = map.getUpperRight().y + 1;

        panes = new Pane[fieldsX][fieldsY];

        size = Math.min(400.0/(double)fieldsX, 400.0/(double)fieldsY);
        for (int i = 0; i < fieldsX; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(size));
        }
        for (int i = 0; i < fieldsY; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(size));
        }

        Pane pane;
        for (int i = 0; i < fieldsX; i++) {
            for (int j = 0; j < fieldsY; j++) {
                pane = new Pane();
                if(map.inJungle(new Vector2d(i, j))){
                    pane.setBackground(jungle);
                }
                else{
                    pane.setBackground(savanna);
                }
                panes[i][fieldsY-1-j] = pane;
                gridPane.add(pane, i, fieldsY-1-j);
            }
        }
        Button dominantGenotypes = new Button("Show animals with dominant genotype");
        dominantGenotypes.setOnAction(event -> {
            if(engine.isEngineStopped()){
                Platform.runLater(()->{
                    List<Vector2d> positions = engine.getDominantPositions();
                    for (Vector2d position: positions) {
                        toRestore.put(position, circles.get(position).getFill());
                        circles.get(position).setFill(highlight);
                    }
                });
            }
        });
        Button save = new Button("Save");
        save.setOnAction(event -> {
            if (engine.isEngineStopped()){
                File file = new File(System.getProperty("user.home")+File.separator+"output.csv");
                try(PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
                    List<Integer> animalsStatistics = statistics.getAnimalsStatistics();
                    List<Integer>grassStatistics = statistics.getGrassStatistics();
                    List<Integer> energyStatistics = statistics.getEnergyStatistics();
                    List<Integer> liveStatistics = statistics.getLiveStatistics();
                    List<Integer> childStatistics = statistics.getChildStatistics();
                    int last = animalsStatistics.size();
                    for (int i = 0; i < last; i++) {
                        String line = "";
                        line = line.concat(String.valueOf(animalsStatistics.get(i)).concat(","));
                        line = line.concat(String.valueOf(grassStatistics.get(i)).concat(","));
                        line = line.concat(String.valueOf(energyStatistics.get(i)).concat(","));
                        line = line.concat(String.valueOf(liveStatistics.get(i)).concat(","));
                        line = line.concat(String.valueOf(childStatistics.get(i)));
                        output.println(line);
                    }
                    String line = "";
                    line = line.concat(String.valueOf(average(animalsStatistics)).concat(","));
                    line = line.concat(String.valueOf(average(grassStatistics)).concat(","));
                    line = line.concat(String.valueOf(average(energyStatistics)).concat(","));
                    line = line.concat(String.valueOf(average(liveStatistics)).concat(","));
                    line = line.concat(String.valueOf(average(childStatistics)));
                    output.println(line);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        vBox = new VBox(gridPane, dominantGenotypes, save);
    }

    public VBox getvBox() {
        return vBox;
    }

    public void restore(){
        Platform.runLater(()->{
            for (Vector2d position :
                    toRestore.keySet()) {
                circles.get(position).setFill(toRestore.get(position));
            }
        });

    }

    private Long average(List<Integer> numbers){
        int avg = 0;
        for (Integer number: numbers) {
            avg += number;
        }
        return Math.round((double) avg/numbers.size());

    }

    private Color animalColor(int energy){
        double k = (double) energy / (double) startEnergy;
        if(k > 0.8) return veryHighEnergy;
        if(k > 0.6) return highEnergy;
        if(k > 0.4) return mediumEnergy;
        if(k > 0.2) return lowEnergy;
        if(k > 0) return veryLowEnergy;
        return noEnergy;
    }

    public void grassRemoved(Vector2d position) {
        Platform.runLater(() ->{
            if(map.inJungle(position)){
                panes[position.x][fieldsY-1-position.y].setBackground(jungle);
            }
            else{
                panes[position.x][fieldsY-1-position.y].setBackground(savanna);
            }
        });
    }

    public void grassAdded(Vector2d position) {
        Platform.runLater(() ->{
            panes[position.x][fieldsY-1-position.y].setBackground(grass);
        });
    }

    @Override
    public void animalLeft(Vector2d position, boolean allGone, int newMaxEnergy) {
        Platform.runLater(()->{
            if(allGone){
                Circle circle = circles.get(position);
                gridPane.getChildren().remove(circle);
                circles.remove(position);
            }
            else{
                updateMaxEnergy(position, newMaxEnergy);
            }
        });
    }

    @Override
    public void animalArrived(Vector2d position, int energy) {
        Platform.runLater(()->{
                if(!circles.containsKey(position)){
                    Circle circle = createCircle(energy, position);
                    circles.put(position, circle);
                    gridPane.add(circle, position.x, fieldsY-1-position.y);
                }
                else {
                    updateMaxEnergy(position, energy);
                }
        });
    }

    @Override
    public void updateMaxEnergy(Vector2d position, int energy) {
        Platform.runLater(()->{
            if(animalColor(energy) != circles.get(position).getFill()){
                circles.get(position).setFill(animalColor(energy));
            }
        });
    }

    private Circle createCircle(int energy, Vector2d position){
        Circle circle = new Circle(size/2, size/2, size/2, animalColor(energy));
        circle.setOnMouseClicked(event -> {
            if(engine.isEngineStopped()){
                statistics.updateAnimalStatistics(getDominantAnimal(position));
            }
        });
        return circle;
    }
    private Animal getDominantAnimal(Vector2d position){
        return map.getAnimals().get(position).peek();
    }
}
