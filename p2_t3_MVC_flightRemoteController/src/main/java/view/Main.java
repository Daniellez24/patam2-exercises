package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Model;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader fxml = new FXMLLoader();
            BorderPane root = fxml.load(getClass().getResource("Window.fxml").openStream());
            //the view class(WindowController) doesn't know the Model and the Controller, because of the Observer design pattern
            WindowController wc = fxml.getController(); // view
            wc.paint();
            // the model doesn't know the controller and the view
            Model m = new Model("properties.txt"); // model
            Controller c = new Controller(m, wc); // controller

            Scene scene = new Scene(root, 300, 300);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
