package application.bdd.enemy;

import application.bdd.tower.TowerHandler;
import javafx.animation.RotateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public abstract class Enemy {
    private int health;
    private final int baseHealth;
    private final float speed;
    private DoubleProperty currentSpeed;
    private final int strength;
    private final int reward;
    private final ImageView image;
    private final double weight;
    private boolean alive;
    private boolean atMonument;

    public Enemy(int health, float speed, int strength,
                 int reward, String imageLocation, double weight) {
        this.health = health;
        this.baseHealth = health;
        this.speed = speed;
        currentSpeed = new SimpleDoubleProperty();
        currentSpeed.set(1);
        this.strength = strength;
        this.weight = weight;
        this.reward = reward;
        this.alive = true;
        this.atMonument = false;
        image = new ImageView(imageLocation);
        image.setVisible(true);
        image.setX(1000);
        image.setY(1000);
    }

    public ImageView getImage() {
        return image;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        alive = health > 0;
        if (alive) {
            updateImageHealth();
        } else {
            image.setVisible(false);

        }

    }

    public float getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public boolean isAlive() {
        return alive;
    }

    public void updateImageHealth() {
        image.setEffect(new Glow(1 - (float) health / baseHealth));
        shakeImage();
    }

    private void shakeImage() {
        RotateTransition shake = new RotateTransition(new Duration(100));
        shake.setFromAngle(-15);
        shake.setToAngle(0);
        shake.setCycleCount(3);
        shake.setNode(image);
        shake.play();
    }

    public double getWeight() {
        return weight;
    }

    public int getReward() {
        return reward;
    }

    public void checkPosition(ArrayList<TowerHandler> towerPositions) {
        for (TowerHandler tower : towerPositions) {
            if (alive && !tower.isShotOnce() && image.getBoundsInParent()
                    .intersects(tower.getRadi().getLayoutBounds())) {
                setEnemyCoordinates(tower);
                tower.getTower().attackEnemy(tower, this);
            }
        }
    }

    public DoubleProperty getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double rate) {
        currentSpeed.set(rate);
    }


    public void setEnemyCoordinates(TowerHandler tower) {
        tower.setCurrentEnemyX((int) (image.getX() + image.getTranslateX()));
        tower.setCurrentEnemyY((int) (image.getY() + image.getTranslateY()));
    }

    public boolean isAtMonument() {
        return atMonument;
    }

    public void setAtMonument(boolean atMonument) {
        this.atMonument = atMonument;
    }

    public boolean getVisibility() {
        return image.isVisible();
    }
}
