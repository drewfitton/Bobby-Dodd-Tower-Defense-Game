package application.bdd.tower;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class TowerHandler {

    private Circle radi;
    private Tower tower;
    private int currentEnemyX;
    private int currentEnemyY;
    private boolean shotOnce;
    private Pane currentScene;

    public TowerHandler(Tower tower, Circle radi, Pane currentScene) {
        this.tower = tower;
        this.radi = radi;
        this.currentScene = currentScene;
        shotOnce = false;
    }


    public Circle getRadi() {
        return radi;
    }

    public void setRadi(Circle radi) {
        this.radi = radi;
    }

    public Tower getTower() {
        return tower;
    }

    public int getCurrentEnemyX() {
        return currentEnemyX;
    }

    public void setCurrentEnemyX(int currentEnemyX) {
        this.currentEnemyX = currentEnemyX;
    }

    public int getCurrentEnemyY() {
        return currentEnemyY;
    }

    public void setCurrentEnemyY(int currentEnemyY) {
        this.currentEnemyY = currentEnemyY;
    }

    public Pane getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Pane currentScene) {
        this.currentScene = currentScene;
    }

    public boolean isShotOnce() {
        return shotOnce;
    }

    public void setShotOnce(boolean shotOnce) {
        this.shotOnce = shotOnce;
    }
}
