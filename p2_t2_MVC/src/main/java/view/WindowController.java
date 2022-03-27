package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Observable;

public class WindowController extends Observable {

    @FXML
    Label resultLabel; // the name of the Label has to be the same as the fx:id of the label in the fxml doc

    public void print() {
        System.out.println("hello world");
    }

    public void generate(){
        setChanged();
        notifyObservers();
    }

    // display the result in the label "result" with the fx:id "resultLabel"
    public void display(int result) {
        resultLabel.textProperty().set(""+result);
    }
}
