package application.bdd.tower;

import application.bdd.BDDGame;
import application.bdd.enemy.Enemy;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class WreckTower extends Tower {

    public WreckTower() {
        this(500);
    }

    public WreckTower(int cost) {
        super(cost, 25, "Ramblin Wreck", String.valueOf(BDDGame.class
                .getResource("ramblinwreckbuzz.PNG")));
        image.setPreserveRatio(true);
        image.setFitWidth(75);
        image.setFitHeight(95);
    }

    @Override
    public void attackEnemy(TowerHandler tower, Enemy enemy) {
        animate(tower);
        enemy.takeDamage(strength);
        tower.setShotOnce(true);
    }

    @Override
    public void upgrade(TowerHandler tower) {
        tower.getTower().setStrength(tower.getTower().getStrength() + 10);
        tower.getRadi().setRadius(tower.getRadi().getRadius() + 30);
        tower.getTower().level++;
    }

    @Override
    public String getUpgradeStats(TowerHandler th) {
        return String.format("Current: %.0f range, %d damage \n Upgraded: %.0f range, %d damage",
                th.getRadi().getRadius(), th.getTower().getStrength(),
                (th.getRadi().getRadius() + 30), (th.getTower().getStrength() + 10));
    }

    @Override
    public void animate(TowerHandler tower) {
        double x1 = tower.getRadi().getCenterX();
        double y1 = tower.getRadi().getCenterY();
        int x2 = tower.getCurrentEnemyX();
        int y2 = tower.getCurrentEnemyY();
        Pane root = tower.getCurrentScene();
        ImageView bullet = new ImageView(String.valueOf(BDDGame.class
                .getResource("bullet.PNG")));
        bullet.setFitWidth(bullet.getFitWidth() * 5);
        bullet.setFitHeight(bullet.getFitHeight() * 5);
        bullet.setX(x1);
        bullet.setY(y1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(200), bullet);
        tt.setByX(x2 - x1);
        tt.setByY(y2 - y1);
        tt.setOnFinished((e) -> {
            bullet.setVisible(false);
            root.getChildren().remove(bullet);
        });
        root.getChildren().add(bullet);
        tt.play();
    }
}
