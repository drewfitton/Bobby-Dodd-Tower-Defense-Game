package application.bdd.controller;

import application.bdd.BDDApp;
import application.bdd.BDDGame;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private BDDGame currentGame;



    // region FXML Variables
    @FXML
    private Button quitButton;
    @FXML
    private Button enterPlayerNameButton;
    @FXML
    private Button returnToMenuButton;
    @FXML
    private JFXButton devButton;
    @FXML
    private Button easyMapButton;
    @FXML
    private Button medMapButton;
    @FXML
    private Button hardMapButton;
    @FXML
    private TextField playerNameInput;
    @FXML
    private Label playerOutputText;
    @FXML
    private ImageView easyMap;
    @FXML
    private ImageView medMap;
    @FXML
    private ImageView hardMap;
    // endregion

    public WelcomeController() {
        currentGame = new BDDGame();
    }

    //region Welcome/Home Screen Stuff

    /**
     * Method to switch the current scene to the configuration screen
     *
     * @param event some event that triggers the switch like a button
     * @throws IOException in case the fxml file is not properly stated
     */
    @FXML
    public void switchToConfigScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BDDApp.class.getResource("configScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = getCurrentStage(event);
        stage.setScene(scene);
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

    /**
     * Method to hold all the initialization of the new Game when a player hits the start button
     *
     * @param event the start button press event
     * @throws IOException incase the fxml file is not properly stated
     */
    @FXML
    public void startGameButtonEvent(ActionEvent event) throws IOException {
        switchToConfigScreen(event);
    }

    //endregion

    //region Config Screen

    /**
     * Method to switch the selected map/difficulty as Map 1/easy
     *
     * @param event some event that triggers the switch like a button
     * @throws IOException in case the fxml file is not properly stated
     */
    @FXML
    public void selectMap1(ActionEvent event) throws IOException {
        currentGame.setDifficulty(1);
        easyMap.setEffect(glowImage());
        medMap.setEffect(null);
        hardMap.setEffect(null);

    }

    /**
     * Method to switch the selected map/difficulty as Map 2/medium
     *
     * @param event some event that triggers the switch like a button
     * @throws IOException in case the fxml file is not properly stated
     */
    @FXML
    public void selectMap2(ActionEvent event) throws IOException {
        currentGame.setDifficulty(2);
        easyMap.setEffect(null);
        medMap.setEffect(glowImage());
        hardMap.setEffect(null);
    }

    /**
     * Method to switch the selected map/difficulty as Map 3/hard
     *
     * @param event some event that triggers the switch like a button
     * @throws IOException in case the fxml file is not properly stated
     */
    @FXML
    public void selectMap3(ActionEvent event) throws IOException {
        currentGame.setDifficulty(4);
        easyMap.setEffect(null);
        medMap.setEffect(null);
        hardMap.setEffect(glowImage());
    }

    /**
     * Switch the current scene to the welcome/home screen
     *
     * @param event some event that triggers the switch like a button
     * @throws IOException in case the fxml file is not properly stated
     */
    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BDDApp.class.getResource("welcomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the current player name in the currentGame and checks for disallowed names
     *
     * @param event event from start game button
     * @throws IOException in case the fxml file in invalid
     */
    @FXML
    public void enterPlayerName(ActionEvent event) throws IOException {
        String name = playerNameInput.getText();
        if (name == null || name.strip().equals("")) {
            playerOutputText.setText("Invalid Player Name");
            playerOutputText.setFont(new Font(15));
        } else if (currentGame.getDifficulty() == 0) {
            playerOutputText.setText("Please select a difficulty");
            playerOutputText.setFont(new Font(15));
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "",
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Start game?");
            alert.setHeaderText(("Start game as: " + name
                    + " and " + currentGame.difficultyToString() + " difficulty?"));
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                currentGame.setPlayerName(name);
                startCurrentGame(event);
            } else {
                alert.close();
            }

        }
    }

    @FXML
    public void startDevGame(ActionEvent event) {
        currentGame.setDifficulty(1);
        currentGame.setPlayerName("Evan");
        FXMLLoader loader = currentGame.getMap();
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = getCurrentStage(event);
        stage.setScene(scene);
        stage.setX(100);
        stage.setY(100);
        GameMapController newController = loader.getController();
        newController.setCurrentGame(currentGame);
        newController.init();
        stage.show();
    }

    //endregion

    //region Helper Methods

    /**
     * Method to handle changing to the new Game controller and new Game Scene
     *
     * @param event the event that triggered this
     * @throws IOException in case the fxml file is invalid
     */
    private void startCurrentGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BDDApp.class.getResource("loadingScreen.fxml"));
        Scene scene = new Scene(loader.load());
        stage = getCurrentStage(event);
        stage.setScene(scene);
        stage.setX(100);
        stage.setY(100);
        LoadingController newController = loader.getController();
        newController.setCurrentGame(currentGame);
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
     * Makes a dropshadow that is similar to a glow
     *
     * @return a dropshadow
     */
    public DropShadow glowImage() {
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        borderGlow.setColor(Color.BLUE);
        borderGlow.setWidth(70);
        borderGlow.setHeight(70);
        return borderGlow;
    }


    /**
     * Setter for changing the current game; currently unused
     *
     * @param currentGame the new game
     */
    public void setCurrentGame(BDDGame currentGame) {
        this.currentGame = currentGame;
    }
    //endregion
}
