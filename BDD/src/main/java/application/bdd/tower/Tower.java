package application.bdd.tower;

import application.bdd.enemy.Enemy;
import javafx.scene.image.ImageView;

public abstract class Tower {
    protected ImageView image;
    protected int cost;
    protected int strength;
    protected int level;
    protected String name;

    public Tower(int cost, int strength, String name, String imageLocation) {
        this.cost = cost;
        this.strength = strength;
        this.image = new ImageView(imageLocation);
        level = 1;
        this.name = name;
    }

    public ImageView getImage() {
        return image;
    }

    public int getCost() {
        return cost;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = Math.max(strength, 0);
    }

    public void attackEnemy(TowerHandler tower, Enemy enemy) {
    }

    public void animate(TowerHandler tower) {
    }

    public String getTowerName() {
        return String.format("%s - %d", name, level);
    }

    public String getName() {
        return name;
    }

    public int getUpgradeCost() {
        return (level * 2) * (cost / 5);
    }

    public void setLevel(int i) {
        level = i;
    }

    public abstract void upgrade(TowerHandler th);
    public abstract String getUpgradeStats(TowerHandler th);
}
