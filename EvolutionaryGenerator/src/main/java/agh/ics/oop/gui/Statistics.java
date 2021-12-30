package agh.ics.oop.gui;

import agh.ics.oop.IAnimalChangeObserver;
import agh.ics.oop.Vector2d;
import agh.ics.oop.mapElement.Animal;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public class Statistics implements ISimulationObserver, IAnimalChangeObserver {
    private final int daysToShow = 20;

    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final List<Integer> animalData = new LinkedList<>();
    private final List<Integer> grassData = new LinkedList<>();
    private final XYChart.Series<Number, Number> animalSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> grassSeries = new XYChart.Series<>();
    private final Text genotypeText = new Text("");
    private final Text energyText = new Text("");
    private final Text liveText = new Text("");
    private final Text childrenText = new Text("");
    private final VBox vBox;

    private final Text genotypeTextAnimal = new Text();
    private final Text childrenTextAnimal = new Text();
    private final Text descendantsTextAnimal = new Text();
    private final Text ageTextAnimal = new Text();

    private Animal currentAnimal = null;

    private final List<Integer> animalsStatistics = new LinkedList<>();
    private final List<Integer> grassStatistics = new LinkedList<>();
    private final List<Integer> energyStatistics = new LinkedList<>();
    private final List<Integer> liveStatistics = new LinkedList<>();
    private final List<Integer> childStatistics = new LinkedList<>();

    public Statistics(SimulationEngine engine) {
        engine.addSimulationObserver(this);

        xAxis.setTickUnit(1);
        yAxis.setTickUnit(10);
        animalSeries.setName("Animals");
        grassSeries.setName("Grass");
        lineChart.getData().add(animalSeries);
        lineChart.getData().add(grassSeries);

        lineChart.setMaxSize(300, 300);
        Label genotypeLabel = new Label("Dominant genotype:");
        genotypeLabel.setFont(Font.font(15));
        genotypeText.setFont(Font.font(7));
        Label energyLabel = new Label("Average energy:");
        energyLabel.setFont(Font.font(15));
        energyText.setFont(Font.font(15));
        Label liveLabel = new Label("Average live length:");
        liveLabel.setFont(Font.font(15));
        liveText.setFont(Font.font(15));
        Label childrenLabel = new Label("Average number of children:");
        childrenLabel.setFont(Font.font(15));
        childrenText.setFont(Font.font(15));

        Label genotypeLabelAnimal = new Label("Genotype of Chosen Animal");
        Label childrenLabelAnimal = new Label("Children");
        Label descendantsLabelAnimal = new Label("Descendants");
        Label ageLabelAnimal = new Label("Day of death");

        genotypeTextAnimal.setFont(Font.font(7));

        vBox = new VBox(lineChart,
                genotypeLabel, genotypeText,
                new HBox(energyLabel, energyText),
                new HBox(liveLabel, liveText),
                new HBox(childrenLabel, childrenText),
                genotypeLabelAnimal, genotypeTextAnimal,
                new HBox(childrenLabelAnimal, childrenTextAnimal),
                new HBox(descendantsLabelAnimal, descendantsTextAnimal),
                new HBox(ageLabelAnimal, ageTextAnimal)
                );

    }

    public VBox getVBox() {
        return vBox;
    }

    public int getDaysToShow() {
        return daysToShow;
    }

    public List<Integer> getAnimalsStatistics() {
        return animalsStatistics;
    }

    public List<Integer> getGrassStatistics() {
        return grassStatistics;
    }

    public List<Integer> getEnergyStatistics() {
        return energyStatistics;
    }

    public List<Integer> getLiveStatistics() {
        return liveStatistics;
    }

    public List<Integer> getChildStatistics() {
        return childStatistics;
    }

    @Override
    public void updateStatistics(int day, int nOfAnimals, int nOfGrass, Integer[] dominantGenotype,
                                 double averageEnergy, double averageLiveLength, double averageChildren) {
        animalsStatistics.add(nOfAnimals);
        grassStatistics.add(nOfGrass);
        energyStatistics.add((int) Math.round(averageEnergy));
        liveStatistics.add((int) Math.round(averageLiveLength));
        childStatistics.add((int) Math.round(averageChildren));
        Platform.runLater(() -> {
            animalSeries.getData().clear();
            grassSeries.getData().clear();
            if (animalData.size() > daysToShow) {
                animalData.remove(0);
                grassData.remove(0);
            }

            animalData.add(nOfAnimals);
            grassData.add(nOfGrass);

            int start = day > daysToShow ? day - daysToShow : 1;

            xAxis.setLowerBound(start);
            xAxis.setUpperBound(start + daysToShow);
            int i;
            i = 0;
            for (Integer n : animalData) {
                animalSeries.getData().add(new XYChart.Data<>(start + i, n));
                i++;
            }
            i = 0;
            for (Integer n : grassData) {
                grassSeries.getData().add(new XYChart.Data<>(start + i, n));
                i++;
            }

            String genotype = "";
            for (Integer gene :
                    dominantGenotype) {
                genotype = genotype.concat(gene.toString()).concat(" ");
            }
            genotypeText.setText(genotype);
            energyText.setText(String.valueOf(Math.round(averageEnergy)));
            liveText.setText(String.valueOf(Math.round(averageLiveLength)));
            childrenText.setText(String.valueOf(Math.round(averageChildren)));
        });
    }

    public void updateAnimalStatistics(Animal animal){
        Platform.runLater(()->{
            String genotype = "";
            for (Integer gene : animal.getGenotype().getGenes()) {
                genotype = genotype.concat(gene.toString()).concat(" ");
            }
            genotypeTextAnimal.setText(genotype);
            if(currentAnimal != null){
                currentAnimal.removeAnimalObserver(this);
            }

            currentAnimal = animal;
            animal.addAnimalObserver(this);
        });

    }

    @Override
    public void energyChanged(Animal animal, int oldEnergy) {

    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition) {

    }

    @Override
    public void animalDied(Animal animal) {
        Platform.runLater(()->{
            ageTextAnimal.setText(String.valueOf(animal.getDayOfBirth()+animal.getAge()));
        });
    }

    @Override
    public void newChild(int children) {
        Platform.runLater(()->{
            childrenTextAnimal.setText(String.valueOf(children));
        });
    }

    @Override
    public void newDescendant(int descendants) {
        Platform.runLater(()->{
            descendantsTextAnimal.setText(String.valueOf(descendants));
        });
    }
}

