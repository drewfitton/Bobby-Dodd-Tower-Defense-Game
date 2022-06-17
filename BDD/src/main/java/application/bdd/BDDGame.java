package application.bdd;

import application.bdd.enemy.Enemy;
import application.bdd.tower.BuzzTower;
import application.bdd.tower.ElectricTower;
import application.bdd.tower.Tower;
import application.bdd.tower.WreckTower;
import javafx.fxml.FXMLLoader;

public class BDDGame {

    private String playerName;
    private FXMLLoader map;
    private int difficulty;
    private int health;
    private int money;
    private int tower1Cost;
    private int tower2Cost;
    private int tower3Cost;


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public FXMLLoader getMap() {
        return map;
    }

    public void setMap(FXMLLoader map) {
        this.map = map;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String difficultyToString() {
        if (difficulty == 0) {
            return "Not Set";
        } else if (difficulty == 1) {
            return "Easy";
        } else if (difficulty == 2) {
            return "Medium";
        } else {
            return "Hard";
        }
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        if (difficulty == 1) {
            map = new FXMLLoader(BDDApp.class.getResource("easyMapScreen.fxml"));
            money = 1000;
            health = 200;
            tower1Cost = 100;
            tower2Cost = 200;
            tower3Cost = 500;
        } else if (difficulty == 2) {
            map = new FXMLLoader(BDDApp.class.getResource("easyMapScreen.fxml"));
            money = 500;
            health = 100;
            tower1Cost = 150;
            tower2Cost = 300;
            tower3Cost = 750;
        } else {
            map = new FXMLLoader(BDDApp.class.getResource("easyMapScreen.fxml"));
            money = 250;
            health = 50;
            tower1Cost = 200;
            tower2Cost = 400;
            tower3Cost = 1000;
        }
    }

    public double getHealthPercent() {
        return (float) health / (200f / (float) difficulty);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int add) {
        money += add;
    }

    public boolean buyTower(Tower tower) {
        if (money >= tower.getCost()) {
            money -= tower.getCost();
            return true;
        }
        return false;
    }

    public boolean damageHealth(Enemy enemy) {
        health -= enemy.getStrength();
        return health > 0;
    }

    public boolean buyTower1() {
        return buyTower(new BuzzTower(tower1Cost));
    }

    public boolean buyTower2() {
        return buyTower(new ElectricTower(tower2Cost));
    }

    public boolean buyTower3() {
        return buyTower(new WreckTower(tower3Cost));
    }


    public int getTower1Cost() {
        return tower1Cost;
    }

    public int getTower2Cost() {
        return tower2Cost;
    }

    public int getTower3Cost() {
        return tower3Cost;
    }
}
