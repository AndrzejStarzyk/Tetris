package agh.ics.oop.gui;

import agh.ics.oop.map.BoundedMap;
import agh.ics.oop.map.IMap;
import agh.ics.oop.map.UnboundedMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class App extends Application{
    private static int width;
    private static int height;
    private static double jungleRatio;
    private static int startEnergy;
    private static int moveEnergy;
    private static int plantEnergy;
    private static int animalsAtStart;
    private static int moveDelay;
    private boolean isMagicLeft;
    private boolean isMagicRight;

    private IMap leftMap;
    private IMap rightMap;
    private SimulationEngine leftMapEngine;
    private SimulationEngine rightMapEngine;
    private Thread leftMapThread;
    private Thread rightMapThread;
    private boolean stopLeftThread = false;
    private boolean stopRightThread = false;


    private Map leftMapDisplay;
    private Map rightMapDisplay;
    private Statistics leftStatistics;
    private Statistics rightStatistics;
    private  Scene mainScene;
    private final Stage mainStage = new Stage();

    public void init() {

    }

    public void start(Stage primaryStage) {
        Scene settings = this.crateSettings();
        primaryStage.setScene(settings);
        primaryStage.show();
    }

    private Scene crateSettings(){
        String widthDefault = "10";
        String heightDefault = "10";
        String jungleRatioDefault = "0.1";
        String startEnergyDefault = "40";
        String moveEnergyDefault = "1";
        String plantEnergyDefault = "10";
        String animalsAtStartDefault = "10";
        String moveDelayDefault = "10";
        String isMagicLeftDefault = "false";
        String isMagicRightDefault = "false";
        if (this.getParameters().getRaw().size() > 0){
            String[] parameters = this.getParameters().getRaw().toArray(new String[0]);
            widthDefault = parameters[0];
            heightDefault = parameters[1];
            jungleRatioDefault = parameters[2];
            startEnergyDefault = parameters[3];
            moveEnergyDefault = parameters[4];
            plantEnergyDefault = parameters[5];
            animalsAtStartDefault = parameters[6];
            moveDelayDefault = parameters[7];
            isMagicLeftDefault = parameters[8];
            isMagicRightDefault = parameters[9];
        }


        Label mapSettings = new Label("Map settings:");
        TextField widthTF = new TextField(widthDefault);
        HBox widthBox = new HBox(new Label("Width:"), widthTF);
        TextField heightTF = new TextField(heightDefault);
        HBox heightBox = new HBox(new Label("Height:"), heightTF);
        TextField ratioTF = new TextField(jungleRatioDefault);
        HBox ratioBox = new HBox(new Label("Jungle ratio:"), ratioTF);
        TextField plantEnergyTF = new TextField(plantEnergyDefault);
        HBox plantEnergyBox = new HBox(new Label("PlantEnergy:"), plantEnergyTF);
        TextField animalsAtStartTF = new TextField(animalsAtStartDefault);
        HBox animalsAtStartBox = new HBox(new Label("Animals at start:"), animalsAtStartTF);

        Label animalSettings = new Label("Animal settings");
        TextField startEnergyTF = new TextField(startEnergyDefault);
        HBox startEnergyBox = new HBox(new Label("Start energy:"), startEnergyTF);
        TextField moveEnergyTF = new TextField(moveEnergyDefault);
        HBox moveEnergyBox = new HBox(new Label("Move Energy:"), moveEnergyTF);

        Label general = new Label("General:");
        TextField leftMagicTF = new TextField(isMagicLeftDefault);
        HBox leftMagicBox = new HBox(new Label("Magic mode for left map:"), leftMagicTF);
        TextField rightMagicTF = new TextField(isMagicRightDefault);
        HBox rightMagicBox = new HBox(new Label("Magic mode for right map:"), rightMagicTF);
        TextField moveDelayTF = new TextField(moveDelayDefault);
        HBox moveDelayBox = new HBox(new Label("Move delay:"), moveDelayTF);

        Button stopLeftSimulation = new Button("Stop");
        Button stopRightSimulation = new Button("Stop");
        stopLeftSimulation.setOnAction(event -> {
            stopLeftThread = true;
        });
        stopRightSimulation.setOnAction(event -> {
            stopRightThread = true;
        });

        Button resumeLeftSimulation = new Button("Resume");
        Button resumeRightSimulation = new Button("Resume");
        resumeLeftSimulation.setOnAction(event -> {
            synchronized (leftMapEngine){
                leftMapDisplay.restore();
                stopLeftThread = false;
                leftMapEngine.notify();
            }
        });
        resumeRightSimulation.setOnAction(event -> {
            synchronized (rightMapEngine){
                rightMapDisplay.restore();
                stopRightThread = false;
                rightMapEngine.notify();
            }
        });

        Button startButton = new Button("Start simulation");
        startButton.setOnAction(event -> {
            width = Integer.parseInt(widthTF.getText());
            height = Integer.parseInt(heightTF.getText());
            jungleRatio = Double.parseDouble(ratioTF.getText());
            startEnergy= Integer.parseInt(startEnergyTF.getText());
            moveEnergy = Integer.parseInt(moveEnergyTF.getText());
            plantEnergy = Integer.parseInt(plantEnergyTF.getText());
            animalsAtStart = Integer.parseInt(animalsAtStartTF.getText());
            moveDelay = Integer.parseInt(moveDelayTF.getText());
            isMagicLeft = Boolean.parseBoolean(leftMagicTF.getText());
            isMagicRight= Boolean.parseBoolean(rightMagicTF.getText());


            leftMap = new UnboundedMap(width, height);
            rightMap = new BoundedMap(width, height);
            leftMapEngine = new SimulationEngine(leftMap, this, isMagicLeft);
            rightMapEngine = new SimulationEngine(rightMap, this, isMagicRight);
            leftStatistics = new Statistics(leftMapEngine);
            rightStatistics = new Statistics(rightMapEngine);
            leftMapDisplay = new Map(leftMap, leftStatistics, leftMapEngine);
            rightMapDisplay = new Map(rightMap, rightStatistics, rightMapEngine);

            mainScene = new Scene(new HBox(10,
                    new HBox(leftStatistics.getVBox(), new VBox(leftMapDisplay.getvBox(),
                            new HBox(stopLeftSimulation, resumeLeftSimulation))),
                    new HBox(rightStatistics.getVBox(), new VBox(rightMapDisplay.getvBox(),
                            new HBox(stopRightSimulation, resumeRightSimulation)))));
            mainStage.setScene(mainScene);
            mainStage.show();

            leftMapThread = new Thread(leftMapEngine);
            rightMapThread = new Thread(rightMapEngine);
            leftMapThread.start();
            rightMapThread.start();
        });



        VBox settings = new VBox(mapSettings, widthBox, heightBox, ratioBox, animalsAtStartBox, animalSettings,
                                startEnergyBox, moveEnergyBox, plantEnergyBox, general, moveDelayBox, leftMagicBox, rightMagicBox,
                startButton);

        return new Scene(settings, 1000, 600);
    }

    public boolean checkForStop(SimulationEngine engine){
        synchronized (leftMapEngine){
            if(engine.equals(leftMapEngine) && stopLeftThread){
                return true;
            }
        }
        synchronized (rightMapEngine){
            if(engine.equals(rightMapEngine) && stopRightThread){
                return true;
            }
        }
        return false;
    }

    public boolean isStopLeftThread() {
        return stopLeftThread;
    }

    public boolean isStopRightThread() {
        return stopRightThread;
    }

    public static double getJungleRatio() {
        return jungleRatio;
    }

    public static int getStartEnergy() {
        return startEnergy;
    }

    public static int getMoveEnergy() {
        return moveEnergy;
    }

    public static int getPlantEnergy() {
        return plantEnergy;
    }

    public static int getAnimalsAtStart() {
        return animalsAtStart;
    }

    public static int getMoveDelay() {
        return moveDelay;
    }
}
