package application.bdd.enemy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EnemyHandler {
    private final ArrayList<Enemy> currentEnemies;
    private final Enemy[] possibleEnemies;
    private final int[] enemyCount;

    public EnemyHandler(int roundNumber) {
        possibleEnemies = new Enemy[]{new PittsburghPanthers(), new BostonEagle(),
            new DukeDevil(), new GeorgiaBullDog()};
        currentEnemies = new ArrayList<>(roundNumber);
        enemyCount = new int[3];
        for (int i = 0; i < roundNumber; i++) {
            currentEnemies.add(getNewEnemy());
        }
        if (roundNumber == 30) {
            currentEnemies.add(possibleEnemies[3]);
        }
    }

    private Enemy getNewEnemy() {
        int index = 0;
        for (double r = Math.random(); index < 3; ++index) {
            r -= possibleEnemies[index].getWeight();
            if (r <= 0.0) {
                break;
            }
        }
        enemyCount[index]++;
        try {
            return possibleEnemies[index].getClass()
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Enemy[] getPossibleEnemies() {
        return possibleEnemies;
    }

    public ArrayList<Enemy> getCurrentEnemies() {
        return currentEnemies;
    }

    public String currentEnemiesToString() {
        return String.format("%d Panthers, %d Eagles, %d Blue Devils",
                enemyCount[0], enemyCount[1], enemyCount[2]);
    }

}
