module gofish.gofish {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens base to javafx.fxml;
    exports base;
}