package application.bdd.enemy;

import application.bdd.BDDGame;

public class BostonEagle extends Enemy {

    public BostonEagle() {
        this(10, 0.7f, 1, 25);
    }

    public BostonEagle(int health, float speed, int strength, int reward) {
        super(health, speed, strength, reward, String.valueOf(BDDGame.class
                .getResource("bostonEagles.png")), .3);
    }
}
