module view.p2_t5_mvvm_flightremotecontroller {
    requires javafx.controls;
    requires javafx.fxml;


    opens view to javafx.fxml;
    exports view;
}