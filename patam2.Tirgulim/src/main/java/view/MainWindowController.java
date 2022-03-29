package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    int[][] mazeData = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1}
    };

    // initialization of this object mazeDisplayer of type MazeDisplayer here happens in the fxml file
    @FXML
    MazeDisplayer mazeDisplayer;

    /* the difference between the constructor and the initialize method is that the controller calls the constructor only once
    (when initializing the first time), and on the contrary we can call the initialize method everytime something happens and we need
    to initialize the maze
    */
    // so for now we don't need the constructor, all we need is the initialize method
//    public MainWindowController() {
//    }

    // Initializable's method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mazeDisplayer.setMazeData(mazeData);

        // request focus on the maze itself and not the start/stop buttons (so we can move the Character with the arrows keys)
        mazeDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> mazeDisplayer.requestFocus());

        mazeDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int r = mazeDisplayer.getcRow();
                int c = mazeDisplayer.getcCol();

                if (event.getCode() == KeyCode.UP) {
                    mazeDisplayer.setCharacterPosition(r - 1, c);
                }
                if (event.getCode() == KeyCode.DOWN) {
                    mazeDisplayer.setCharacterPosition(r + 1, c);
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    mazeDisplayer.setCharacterPosition(r, c + 1);
                }
                if (event.getCode() == KeyCode.LEFT) {
                    mazeDisplayer.setCharacterPosition(r, c - 1);
                }

            }
        });
    }


    public void start() {
        System.out.println("start");
    }

    public void openFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("open maze file");
        // setting default directory when opening the "open file" dialogue
        fc.setInitialDirectory(new File("src/main/resources"));

//        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("XML", "*.xml"));
        // get extensions filters is a list inside FileChooser (for this tirgul the fxml and all files aren't necessary)
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("FXML", "*.fxml"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("all files", "*.*"));

        File chosen = fc.showOpenDialog(null); // need to fix null
        if (chosen != null) {
            System.out.println(chosen.getName());
        }
    }


}