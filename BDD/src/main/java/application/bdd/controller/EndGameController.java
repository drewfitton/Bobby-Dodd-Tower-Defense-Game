package application.bdd.controller;

import application.bdd.BDDApp;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameController {

    @FXML
    private JFXButton restartButton;
    @FXML
    private JFXButton quitButton;
    @FXML
    private Label endGameLabel;
    @FXML
    private Label gameStats;

    @FXML
    public void restartGame(ActionEvent event) throws IOException {
        Stage stage = getCurrentStage(event);
        FXMLLoader fxmlLoader = new FXMLLoader(BDDApp.class.getResource("welcomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(BDDApp.class
                .getResource("buzz-logo-icon.png"))));
        stage.show();
    }

    /**
     * Closes the stage and current window
     *
     * @param event some event that triggers the close like a button
     */
    @FXML
    public void quitGame(ActionEvent event) {
        Stage stage = getCurrentStage(event);
        stage.close();
    }

    public void init(String gameLabelText, String stats) {
        endGameLabel.setText(gameLabelText);
        gameStats.setText(stats);
    }

    /**
     * Returns the current stage
     *
     * @param event some event that triggers the switch like a button
     * @return current stage
     */
    private Stage getCurrentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
