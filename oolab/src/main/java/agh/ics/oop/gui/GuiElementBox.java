package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Grass;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    public VBox vBox;

    GuiElementBox(IMapElement mapElement){
        try{
            Text label;
            Image image;
            if(mapElement instanceof Grass){
                label = new Text("Trawa");
                image = new Image(new FileInputStream(mapElement.getImage()));
            }
            else{
                label = new Text(((Animal) mapElement).toStringWithPosition());
                image = new Image(new FileInputStream(mapElement.getImage()));
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);

            vBox = new VBox(imageView, label);
            vBox.setAlignment(Pos.CENTER);
            vBox.setMaxHeight(20);
            vBox.setMaxWidth(20);
        }
        catch (FileNotFoundException fileNotFoundException){
            System.out.println(fileNotFoundException.getMessage());
            System.exit(0);
        }

    }
}
