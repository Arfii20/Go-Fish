module gofish.gofish {
    requires javafx.controls;
    requires javafx.fxml;


    opens gofish.driver to javafx.fxml;
    exports gofish.driver;
}