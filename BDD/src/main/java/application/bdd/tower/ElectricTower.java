package application.bdd.tower;

import application.bdd.BDDGame;
import application.bdd.enemy.Enemy;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class ElectricTower extends Tower {


    public ElectricTower() {
        this(200);
    }

    public ElectricTower(int cost) {
        super(cost, 2, "Electric Tower", String.valueOf(BDDGame.class
                .getResource("electricbuzz.PNG")));
        image.setPreserveRatio(true);
        image.setFitWidth(85);
        image.setFitHeight(110);
        image.setEffect(new Glow(3));
    }
    @Override
    public void attackEnemy(TowerHandler tower, Enemy enemy) {
        double enemySlowdown = (1f / strength);
        enemy.setCurrentSpeed(enemySlowdown);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                enemy.setCurrentSpeed(1);
            }
        }, 750);
        animate(tower);
    }

    @Override
    public void upgrade(TowerHandler tower) {
        tower.getTower().setStrength(tower.getTower().getStrength() * 2);
        tower.getRadi().setRadius(tower.getRadi().getRadius() + 20);
        tower.getTower().level++;
    }

    @Override
    public String getUpgradeStats(TowerHandler th) {
        return String.format("Current: %.0f range, %.2f slowdown \n "
                + "Upgraded: %.0f range, %.2f slowdown",
                th.getRadi().getRadius(), (1f / th.getTower().getStrength()),
                (th.getRadi().getRadius() + 20), (1f / (th.getTower().getStrength() * 2)));
    }

    @Override
    public void animate(TowerHandler tower) {
        Circle radius = tower.getRadi();
        double currRadius = radius.getRadius();
        Glow glow = new Glow(0);
        radius.setEffect(glow);
        Timeline discharge = new Timeline(new KeyFrame(Duration.ZERO,
                new KeyValue(glow.levelProperty(), 0),
                new KeyValue(radius.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(glow.levelProperty(), 1),
                        new KeyValue(radius.radiusProperty(), currRadius)));
        radius.setVisible(true);
        discharge.setOnFinished(e -> {
            radius.setVisible(false);
            radius.setEffect(null);
        });
        discharge.play();
    }

    public void permaSlow(Enemy enemy) {
        double enemySlowdown = (1f / strength);
        enemy.setCurrentSpeed(enemySlowdown);
    }

}
