module view.p2_t3_mvc_flightremotecontroller {
    requires javafx.controls;
    requires javafx.fxml;


    opens view to javafx.fxml;
    exports view;
}