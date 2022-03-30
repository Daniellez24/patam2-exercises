package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Model;
import view_model.ViewModel;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader fxml = new FXMLLoader();
            BorderPane root = fxml.load(getClass().getResource("Window.fxml").openStream());
            Model m = new Model("properties.txt"); // model
            WindowController wc = fxml.getController(); // view
            ViewModel vm = new ViewModel(m);
            wc.init(vm);
            wc.paint();

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
