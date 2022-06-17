package application.bdd.controller;

import application.bdd.BDDGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadingController {

    private BDDGame currentGame;

    @FXML
    private ProgressBar gameProgressBar;
    @FXML
    private Button startGameButton;

    @FXML
    private void startProgressBar() {
        gameProgressBar.setProgress(1);
        startGameButton.setVisible(true);
    }

    /**
     * Method to handle changing to the new Game controller and new Game Scene
     *
     * @param event the event that triggered this
     * @throws IOException in case the fxml file is invalid
     */
    @FXML
    private void startCurrentGame(ActionEvent event) throws IOException {
        FXMLLoader loader = currentGame.getMap();
        Scene scene = new Scene(loader.load());
        Stage stage = getCurrentStage(event);
        stage.setScene(scene);
        GameMapController newController = loader.getController();
        newController.setCurrentGame(currentGame);
        newController.init();
        stage.show();
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

    /**
     * Setter for changing the current game
     *
     * @param currentGame the new game
     */
    public void setCurrentGame(BDDGame currentGame) {
        this.currentGame = currentGame;
    }
}
