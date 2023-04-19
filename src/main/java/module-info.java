module dev.riko.golftourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    opens dev.riko.golftourplanner to javafx.fxml;
    exports dev.riko.golftourplanner;
    exports dev.riko.golftourplanner.controlers;
    opens dev.riko.golftourplanner.controlers to javafx.fxml;
}