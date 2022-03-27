package t2_MVC.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import t2_MVC.controller.Controller;
import t2_MVC.model.Model;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxml = new FXMLLoader();
            BorderPane root = fxml.load(getClass().getResource("t2MVC_Window.fxml").openStream());
            //the view class(WindowController) doesn't know the Model and the Controller, because of the Observer design pattern
            WindowController wc = fxml.getController(); // view
            // the model doesn't know the controller and the view
            Model m = new Model(); // model
            Controller c = new Controller(m, wc); // controller

            Scene scene = new Scene(root, 300, 300);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

