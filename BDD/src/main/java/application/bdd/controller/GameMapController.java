package application.bdd.controller;

import application.bdd.BDDApp;
import application.bdd.BDDGame;
import application.bdd.misc.SpriteAnimation;
import application.bdd.enemy.Enemy;
import application.bdd.enemy.EnemyHandler;
import application.bdd.tower.BuzzTower;
import application.bdd.tower.ElectricTower;
import application.bdd.tower.Tower;
import application.bdd.tower.TowerHandler;
import application.bdd.tower.WreckTower;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameMapController {

    private BDDGame currentGame;
    private HamburgerBasicCloseTransition transition;
    private PixelReader placePixelReader;
    private PixelBuffer<IntBuffer> pixelBuffer;
    private int[] pixels;
    private ImageView newTowerImage;
    private Tower newTower;
    private int roundNumber = 1;
    private EnemyHandler currentEnemyHandler;
    private ArrayList<TowerHandler> currentTowerList;
    private ExecutorService spawnExecutor;
    private int killedEnemys = 0;
    private int moneySpent = 0;
    private int upgradesBought = 0;

    @FXML
    private Pane root;
    @FXML
    private ProgressBar healthBar;
    @FXML
    private Label healthBarLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label noMoneyLabel;
    @FXML
    private Pane arrow;
    @FXML
    private Path enemyPath;
    @FXML
    private JFXHamburger menuButton;
    @FXML
    private JFXDrawer menuDrawer;
    @FXML
    private JFXButton tower1Button;
    @FXML
    private Circle towerRadius;
    @FXML
    private JFXButton startRoundButton;
    @FXML
    private Label roundNumberLabel;
    @FXML
    private Label upNextEnemyLabel;
    @FXML
    private Label upgradeTowerNameLabel;
    @FXML
    private Label upgradeTowerCost;
    @FXML
    private Label upgradeTowerStatsLabel;
    @FXML
    private ToolBar upgradeToolbar;
    @FXML
    private ToolBar roundToolbar;
    @FXML
    private JFXButton upgradeButton;




    @FXML
    public void startRound() {
        startRoundButton.setOnAction(e -> { });
        startRoundButton.setText("Running...");
        spawnEnemies(currentEnemyHandler);
        spawnExecutor.submit(() -> {
            if (roundNumber <= 30) {
                startRoundButton.setOnAction(e -> startRound());
                Platform.runLater(() -> startRoundButton.setText("Start Game"));
            } else {
                Platform.runLater(() -> startRoundButton.setText("Last Round"));
            }

        });
        if (roundNumber < 30) {
            roundNumber++;
            currentEnemyHandler = new EnemyHandler(roundNumber);
            upNextEnemyLabel.setText(currentEnemyHandler.currentEnemiesToString());
            roundNumberLabel.setText(String.valueOf(roundNumber));
        } else {
            upNextEnemyLabel.setText("Defeat the Final Boss!");
            roundNumberLabel.setText("30");
        }

    }


    @FXML
    public void updateHealthBar() {
        healthBar.setProgress(currentGame.getHealthPercent());
        healthBarLabel.setText(currentGame.getHealth() + "/"
                + (200f / (float) currentGame.getDifficulty()));
    }

    @FXML
    public void updateMoneyLabel() {
        moneyLabel.setText("Money: $" + currentGame.getMoney());
    }

    @FXML
    public void showHealthBarLabel() {
        healthBarLabel.setVisible(!healthBarLabel.isVisible());
    }

    @FXML
    public void activateMenuDrawer() {
        transition.setRate(transition.getRate() * -1);
        transition.play();
        menuDrawer.toggle();
    }
    @FXML
    public void buyTower1(MouseEvent event) {
        buyTower(new BuzzTower(currentGame.getTower1Cost()), event);
    }

    @FXML
    public void buyTower2(MouseEvent event) {
        buyTower(new ElectricTower(currentGame.getTower2Cost()), event);
    }

    @FXML
    public void buyTower3(MouseEvent event) {
        buyTower(new WreckTower(currentGame.getTower3Cost()), event);
    }







    private void buyTower(Tower tower, MouseEvent event) {
        if (currentGame.buyTower(tower)) {
            updateMoneyLabel();
            noMoneyLabel.setVisible(false);
            newTowerImage = tower.getImage();
            newTower = tower;
            root.getChildren().add(newTowerImage);
            towerDrag(event);
        } else {
            noMoneyLabel.setVisible(true);
            moneyLabelVisibility();
        }
    }

    private void moneyLabelVisibility() {
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
        visiblePause.setOnFinished(e -> noMoneyLabel.setVisible(false));
        visiblePause.play();
    }


    private void towerDrag(MouseEvent event) {
        newTowerImage.getScene().setOnMouseMoved(e -> {
            towerDragAction(e);
        });
        newTowerImage.getScene().setOnMouseClicked(e -> {
            if (towerRadius.getFill().equals(Paint.valueOf("GREY"))) {
                towerDragActionEnd(e);
            }
        });
        newTowerImage.setX(event.getSceneX() - newTowerImage.getFitWidth() / 2);
        newTowerImage.setY(event.getSceneY() - newTowerImage.getFitHeight() / 2);
        towerRadius.setRadius(100);
        newTowerImage.setVisible(true);
        towerRadius.setVisible(true);
    }

    private void towerDragActionEnd(MouseEvent e) {
        newTowerImage.getScene().setOnMouseMoved(event -> { });
        newTowerImage.getScene().setOnMouseClicked(event2 -> { });
        ImageView newlyPlacedTower = getNewTowerImageView();
        Circle newlyPlacedTowerRadi = copyTowerRadi();
        newlyPlacedTower.setOnMouseEntered(event -> {
            newlyPlacedTowerRadi.setVisible(true);
        });
        newlyPlacedTower.setOnMouseExited(event -> {
            newlyPlacedTowerRadi.setVisible(false);
        });
        TowerHandler th = new TowerHandler(newTower, newlyPlacedTowerRadi, root);
        newlyPlacedTower.setOnMouseClicked(event -> {
            upgradeToolBarActivate(th);
        });
        currentTowerList.add(th);
        newTowerImage.setVisible(false);
        towerRadius.setVisible(false);
        root.getChildren().add(newlyPlacedTowerRadi);
        root.getChildren().add(newlyPlacedTower);
        newTowerImage.setX(0);
        newTowerImage.setY(0);
        towerRadius.setRadius(0);
        towerRadius.setCenterX(0);
        towerRadius.setCenterY(0);
    }

    private void upgradeToolBarActivate(TowerHandler th) {
        upgradeTowerNameLabel.setText(th.getTower().getTowerName());
        upgradeTowerCost.setText(String.format("Cost: $%d", th.getTower().getUpgradeCost()));
        upgradeTowerStatsLabel.setText(th.getTower().getUpgradeStats(th));
        upgradeButton.setOnAction(event -> doTowerUpgrade(th));
        upgradeToolbar.setVisible(true);
    }

    public void doTowerUpgrade(TowerHandler th) {
        if (currentGame.getMoney() > th.getTower().getUpgradeCost()) {
            currentGame.addMoney(-1 * th.getTower().getUpgradeCost());
            increaseMoneySpent(th.getTower().getUpgradeCost());
            increaseUpgradesBought();
            updateMoneyLabel();
            th.getTower().upgrade(th);
            upgradeToolBarActivate(th);
        }
    }

    private Circle copyTowerRadi() {
        Circle savedRadi = new Circle();
        savedRadi.setRadius(towerRadius.getRadius());
        savedRadi.setCenterX(towerRadius.getCenterX());
        savedRadi.setCenterY(towerRadius.getCenterY());
        savedRadi.setFill(towerRadius.getFill());
        savedRadi.setOpacity(towerRadius.getOpacity());
        savedRadi.setVisible(false);
        return savedRadi;
    }

    private ImageView getNewTowerImageView() {
        ImageView newlyPlacedTower = new ImageView(newTowerImage.getImage());
        int newX = (int) newTowerImage.getX();
        int newY = (int) newTowerImage.getY();
        int fitX = (int) newTowerImage.getFitWidth();
        int fitY = (int) newTowerImage.getFitHeight();
        newlyPlacedTower.setLayoutX(newX);
        newlyPlacedTower.setLayoutY(newY);
        newlyPlacedTower.setPreserveRatio(true);
        newlyPlacedTower.setFitWidth(fitX);
        newlyPlacedTower.setFitHeight(fitY);
        newlyPlacedTower.setEffect(newTowerImage.getEffect());
        clearTowerPlaceImage(newX + fitX / 4, newY + fitY / 4,
            fitX / 2, fitY / 2);
        return newlyPlacedTower;
    }

    private void towerDragAction(MouseEvent e) {
        double newX = e.getSceneX();
        double newY = e.getSceneY();
        newTowerImage.setX(newX - newTowerImage.getFitWidth() / 2);
        newTowerImage.setY(newY - newTowerImage.getFitHeight() / 2);
        towerRadius.setCenterX(newX);
        towerRadius.setCenterY(newY);
        if (placePixelReader.getColor((int) newX, (int) newY).equals(Color.TRANSPARENT)) {
            towerRadius.setFill(Paint.valueOf("RED"));
        } else {
            towerRadius.setFill(Paint.valueOf("GREY"));
        }
    }



    public void init() {
        updateHealthBar();
        updateMoneyLabel();
        enterScreenArrowAnimate();
        setupMenuDrawer();
        setupWindowClose();
        setupRoundDisplay();
        //Set up the pixelreader to see if the cursor is within the boundary for placing a tower
        Image towerPlaceImage = new Image(String.valueOf(BDDApp.class
                .getResource("bobbyDoddTowerArea.png")));
        placePixelReader = towerPlaceImage.getPixelReader();
        initTowerPlaceImage(towerPlaceImage);
        spawnExecutor = Executors.newFixedThreadPool(1);
        currentTowerList = new ArrayList<>();
        roundNumber = 28;
        for (Node n : root.getChildren()) {
            n.setOnMouseClicked(e -> {
                roundToolbar.setVisible(true);
                upgradeToolbar.setVisible(false);
            });
        }
    }

    private void setupRoundDisplay() {
        currentEnemyHandler = new EnemyHandler(roundNumber);
        roundNumberLabel.setText("1");
        upNextEnemyLabel.setText(currentEnemyHandler.currentEnemiesToString());
    }

    private void initTowerPlaceImage(Image original) {
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        pixels = new int[width * height];
        pixelBuffer = new PixelBuffer<>(width, height, IntBuffer.wrap(pixels),
            PixelFormat.getIntArgbPreInstance());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = placePixelReader.getArgb(x, y);
            }
        }
        pixelBuffer.updateBuffer(buff -> null);
        placePixelReader = new WritableImage(pixelBuffer).getPixelReader();
    }

    private void clearTowerPlaceImage(int startX, int startY, int endX, int endY) {
        for (int y = startY; y < endY + startY; y++) {
            for (int x = startX; x < endX + startX; x++) {
                pixels[x + y * 1140] = 0x00000000;
            }
        }
        pixelBuffer.updateBuffer(buff -> null);
        placePixelReader = new WritableImage(pixelBuffer).getPixelReader();
    }


    /**
     * Makes sure that the Executor Services are shutdown before closing
     */
    private void setupWindowClose() {
        Window currentWindow = root.getScene().getWindow();
        currentWindow.setOnCloseRequest(e -> {
            if (spawnExecutor != null) {
                spawnExecutor.shutdownNow();
            }
            ((Stage) currentWindow).close();
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Sets up the JFX menu drawer being its own separate scene and
     * the menu button
     */
    private void setupMenuDrawer() {
        transition = new HamburgerBasicCloseTransition(menuButton);
        transition.setRate(-1);
        try {
            FXMLLoader menuDrawerLoader = new FXMLLoader(BDDApp.class
                    .getResource("mapMenu.fxml"));
            menuDrawerLoader.setController(this);
            StackPane menuDrawerItems =  menuDrawerLoader.load();
            menuDrawer.setSidePane(menuDrawerItems);
            noMoneyLabel.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter for changing the current game, also resets the health bar and money amt.
     *
     * @param currentGame the new game
     */
    public void setCurrentGame(BDDGame currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * Method to animate the display arrow to show the user the path
     */
    private void enterScreenArrowAnimate() {
        PathTransition arrowPathAnimation = new PathTransition();
        arrowPathAnimation.setDuration(Duration.seconds(20));
        arrowPathAnimation.setNode(arrow);
        arrowPathAnimation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        arrowPathAnimation.setPath(enemyPath);
        arrowPathAnimation.setCycleCount(1);
        arrowPathAnimation.setOnFinished(e -> arrow.setVisible(false));
        arrowPathAnimation.play();
        arrow.setVisible(true);
    }

    /**
     * Large method that allows spawning of enemies
     * Step 1. disables the start round button
     * 2. creates a new EnemyHandler that holds all the current enemies
     * 3. Assigns a runnable to executor service for each enemy
     * 4. each runnable makes a new path transition
     * 5. each path transition activates enemy in monument on finishing
     * 6. resets start button
     * @param enemyHandler which enemyhandler to use to spawn enemies
     */
    private void spawnEnemies(EnemyHandler enemyHandler) {
        Timeline trackEnemy = trackEnemyTimelineGenerator(enemyHandler);
        trackEnemy.setCycleCount(Animation.INDEFINITE);
        trackEnemy.play();

        Timeline trackEnemiesAtMonument = enemyAtMonumentTimeLineGenerator(enemyHandler);
        trackEnemiesAtMonument.setCycleCount(Animation.INDEFINITE);
        trackEnemiesAtMonument.play();

        makeEnemyPathAnimation(enemyHandler);
    }

    private void makeEnemyPathAnimation(EnemyHandler enemyHandler) {
        for (Enemy nextEnemy : enemyHandler.getCurrentEnemies()) {
            ImageView nextEnemyImage = nextEnemy.getImage();
            Runnable spawnEnemy = new Runnable() {
                private Enemy nextEnemy;
                private ImageView nextEnemyImage;

                public Runnable init(Enemy nextEnemy, ImageView nextEnemyImage) {
                    this.nextEnemy = nextEnemy;
                    this.nextEnemyImage = nextEnemyImage;
                    return this;
                }

                @Override
                public void run() {
                    PathTransition nextEnemyPath = new PathTransition();
                    nextEnemyPath.setDuration(Duration.seconds(20f / nextEnemy.getSpeed()));
                    nextEnemyPath.setPath(enemyPath);
                    nextEnemyPath.setNode(nextEnemyImage);
                    nextEnemyPath.rateProperty().bind(nextEnemy.getCurrentSpeed());
                    nextEnemyPath.setOnFinished(aE -> nextEnemy.setAtMonument(true));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                    nextEnemyPath.play();
                }
            }.init(nextEnemy, nextEnemyImage);
            spawnExecutor.execute(spawnEnemy);
            root.getChildren().add(nextEnemyImage);
        }
    }

    /**
     * Method that constantly runs and checks if an enemy is at the monument
     * damages health if one is
     * also does damage to the enemy
     * @param enemyHandler all the enemys to check for
     * @return Timeline the correct timeline that checks each enenmy at the monument
     */
    private Timeline enemyAtMonumentTimeLineGenerator(EnemyHandler enemyHandler) {
        return new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            for (Iterator<Enemy> it = enemyHandler.getCurrentEnemies().iterator(); it.hasNext();) {
                Enemy enemy = it.next();
                if (!enemy.isAtMonument()) {
                    continue;
                }
                if (enemy.isAlive()) {
                    if (!currentGame.damageHealth(enemy)) {
                        swapToEndScreen();
                    } else {
                        updateHealthBar();
                        enemy.takeDamage(10);
                    }
                }
                enemyDeath(it, enemy);
            }
        }));
    }

    private void enemyDeath(Iterator<Enemy> it, Enemy enemy) {
        if (!enemy.isAlive()) {
            currentGame.addMoney(enemy.getReward());
            increaseEnemyKillCount();
            updateMoneyLabel();
            ImageView enemyImage = enemy.getImage();
            deathAnimation(enemyImage.getX()
                    + enemyImage.getTranslateX(), enemyImage.getY()
                    + enemyImage.getTranslateY());
            root.getChildren().remove(enemyImage);
            it.remove();
            if (roundNumber >= 30 && enemy.getReward() == 10000) {
                swapToEndScreen();
            }
        }
    }

    /**
     * Method that constantly runs and checks if an enemy is in range of a tower
     * damages enemy health if it is
     * @param enemyHandler all the enemys to check for
     * @return Timeline the correct timeline that checks each enemy for health
     */
    private Timeline trackEnemyTimelineGenerator(EnemyHandler enemyHandler) {
        return new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            for (Iterator<Enemy> it = enemyHandler.getCurrentEnemies().iterator(); it.hasNext();) {
                Enemy enemy = it.next();
                if (enemy.isAlive()) {
                    enemy.checkPosition(currentTowerList);
                }
                enemyDeath(it, enemy);
            }
            for (TowerHandler t : currentTowerList) {
                t.setShotOnce(false);
            }
        }));
    }

    private void deathAnimation(double x, double y) {
        ImageView deathExplosion = new ImageView(String.valueOf(BDDGame.class
            .getResource("explosion-1.png")));
        deathExplosion.setVisible(true);
        deathExplosion.setViewport(new Rectangle2D(0, 0, 32, 32));
        deathExplosion.setX(x);
        deathExplosion.setY(y);
        SpriteAnimation sA = new SpriteAnimation(deathExplosion, Duration.millis(750), 8,
            8, 32, 32);
        sA.setOnFinished(e -> {
            deathExplosion.setVisible(false);
        });
        sA.play();
        root.getChildren().add(deathExplosion);
    }

    /**
     * Swaps the current screen to the end screen and ends the
     * executor services
     */
    private void swapToEndScreen() {
        if (!spawnExecutor.isShutdown()) {
            FXMLLoader loader = new FXMLLoader(BDDApp.class.getResource("endGameScreen.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            EndGameController endGameController = loader.getController();
            String endMessage;
            if (currentGame.getHealth() > 0) {
                endMessage = "You successfully defended BobbyDodd from those nasty mascots!";
            } else {
                endMessage = "Sorry it looks like you could not defend Bobby Dodd.";
            }
            String endStats = String.format("Total Enemy's Killed: %d, Money Spent $%d, Upgrades Bought %d", killedEnemys, moneySpent, upgradesBought);
            endGameController.init(endMessage, endStats);
            stage.show();
            spawnExecutor.shutdownNow();
        }
    }


    public void increaseEnemyKillCount() {
        killedEnemys++;
    }

    public void increaseMoneySpent(int spent) {
        moneySpent += spent;
    }

    public void increaseUpgradesBought() {
        upgradesBought++;
    }



    public int getKills() {
        return killedEnemys;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public int getUpgradesBought() {
        return upgradesBought;
    }
}
