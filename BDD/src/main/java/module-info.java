module application.bdd {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens application.bdd to javafx.fxml;
    exports application.bdd;
    exports application.bdd.tower;
    opens application.bdd.tower to javafx.fxml;
    exports application.bdd.enemy;
    opens application.bdd.enemy to javafx.fxml;
    exports application.bdd.controller;
    opens application.bdd.controller to javafx.fxml;
    exports application.bdd.misc;
    opens application.bdd.misc to javafx.fxml;
}