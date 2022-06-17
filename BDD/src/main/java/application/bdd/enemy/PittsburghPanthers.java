package application.bdd.enemy;

import application.bdd.BDDGame;

public class PittsburghPanthers extends Enemy {

    public PittsburghPanthers() {
        this(30, 0.5f, 4, 50);
    }

    public PittsburghPanthers(int health, float speed, int strength, int reward) {
        super(health, speed, strength, reward, String.valueOf(BDDGame.class
                .getResource("pittsburghpanther.PNG")), .5);
    }
}
