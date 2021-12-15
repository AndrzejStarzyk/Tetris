package agh.ics.oop.gui;

import javafx.application.*;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;

import java.util.List;

public class App extends Application{
    public void init(){
        List<String> parameters = getParameters().getRaw();
        String[] args = new String[parameters.size()];
        parameters.toArray(args);
        Application.launch(App.class, args);
    }
    public void start(Stage primaryStage){
        Label label1 = new Label("Zwierzak1");
        Label label2 = new Label("Zwierzak2");
        GridPane gridPane = new GridPane();
        gridPane.add(label1, 2, 2);
        gridPane.add(label2, 0, 0);

        gridPane.setGridLinesVisible(true);

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
