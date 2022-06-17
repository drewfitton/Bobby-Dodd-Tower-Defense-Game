package application.bdd.enemy;

import application.bdd.BDDGame;

public class GeorgiaBullDog extends Enemy {

    //Temp testing speed as 10, usually .5

    public GeorgiaBullDog() {
        this(1000, .5f, 50, 10000);
    }

    public GeorgiaBullDog(int health, float speed, int strength, int reward) {
        super(health, speed, strength, reward, String.valueOf(BDDGame.class
                .getResource("georgiaBulldog.PNG")), 0);
    }
}
