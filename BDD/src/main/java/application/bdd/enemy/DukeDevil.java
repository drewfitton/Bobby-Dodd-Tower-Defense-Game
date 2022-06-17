package application.bdd.enemy;

import application.bdd.BDDGame;

public class DukeDevil extends Enemy {

    public DukeDevil() {
        this(50, 0.3f, 10, 200);
    }

    public DukeDevil(int health, float speed, int strength, int reward) {
        super(health, speed, strength, reward, String.valueOf(BDDGame.class
                .getResource("dukebluedevil.PNG")), .2);
    }
}
