import application.bdd.*;
import application.bdd.controller.GameMapController;
import application.bdd.enemy.BostonEagle;
import application.bdd.enemy.DukeDevil;
import application.bdd.enemy.Enemy;
import application.bdd.enemy.EnemyHandler;
import application.bdd.enemy.GeorgiaBullDog;
import application.bdd.enemy.PittsburghPanthers;
import application.bdd.tower.*;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;

public class MilestoneTests {

    private BDDGame currentGame;

    @BeforeAll
    public static void setUpFx() {
        Platform.startup(() -> {
        });
    }

    @BeforeEach
    public void setUpGame() {
        currentGame = new BDDGame();
        currentGame.setDifficulty(1);
    }

    @AfterAll
    public static void exitFx() {
        Platform.exit();
    }



    /*
   M6 - Sonya Yuen
   Tests to make sure that buying an upgrade increases upgradesBought amount
    */
    @Test
    public void testUpgradesBoughtCount() {
        GameMapController gmp = new GameMapController();
        assertEquals(0, gmp.getUpgradesBought());

        gmp.increaseUpgradesBought();
        assertEquals(1, gmp.getUpgradesBought());
    }

    /*
    M6 - Sonya Yuen
    Tests to make sure that the boss is visually different than the other enemies
     */
    @Test
    public void testBossImage() {
        GeorgiaBullDog boss = new GeorgiaBullDog();
        DukeDevil dd = new DukeDevil();
        BostonEagle be = new BostonEagle();
        PittsburghPanthers pp = new PittsburghPanthers();
        assertNotEquals(boss.getImage() , dd.getImage());
        assertNotEquals(boss.getImage() , be.getImage());
        assertNotEquals(boss.getImage() , pp.getImage());
    }
    /*
    M6 - Braden Dunaway
    Tests the atMonument condition for a given enemy.
     */
    @Test
    public void testEnemyAtMonument() {
        GeorgiaBullDog gb = new GeorgiaBullDog();
        assertFalse(gb.isAtMonument());
        gb.setAtMonument(true);
        assertTrue(gb.isAtMonument());
        gb.setAtMonument(false);
        assertFalse(gb.isAtMonument());
    }

    /*
    M6 - Braden Dunaway
    Tests the slowing effect of the Electric tower
     */
    @Test
    public void testEnemySlow() {
        ElectricTower et = new ElectricTower();
        BostonEagle be = new BostonEagle();
        et.permaSlow(be);
        assertTrue(be.getCurrentSpeed().lessThan(be.getSpeed()).get());
    }

    /*
    M6 - Erich Drawdy
    Tests to make sure that the money spent increases each time money is spent
     */
    @Test
    public void testIncreaseMoneySpent() {
        GameMapController gmp = new GameMapController();
        assertEquals(0, gmp.getMoneySpent());
        gmp.increaseMoneySpent(10);
        assertEquals(10, gmp.getMoneySpent());
    }

    /*
    M6 - Andrew Fitton
    Tests to make sure that the boss is stronger than the other enemies
     */
    @Test
    public void testBossStrength() {
        GeorgiaBullDog boss = new GeorgiaBullDog();
        DukeDevil dd = new DukeDevil();
        BostonEagle be = new BostonEagle();
        PittsburghPanthers pp = new PittsburghPanthers();
        assertTrue(boss.getStrength() > dd.getStrength());
        assertTrue(boss.getStrength() > be.getStrength());
        assertTrue(boss.getStrength() > pp.getStrength());
    }

    /*
    M6 - Erich Drawdy
    Tests to make sure that the boss has a reward greater than the other enemies
     */
    @Test
    public void testBossReward() {
        GeorgiaBullDog boss = new GeorgiaBullDog();
        DukeDevil dd = new DukeDevil();
        BostonEagle be = new BostonEagle();
        PittsburghPanthers pp = new PittsburghPanthers();
        assertTrue(boss.getReward() > dd.getReward());
        assertTrue(boss.getReward() > be.getReward());
        assertTrue(boss.getReward() > pp.getReward());
    }

    /*
    M6 - Andrew Fitton
    Tests to make sure that the boss has a health greater than the other enemies
     */
    @Test
    public void testBossHealth() {
        GeorgiaBullDog boss = new GeorgiaBullDog();
        DukeDevil dd = new DukeDevil();
        BostonEagle be = new BostonEagle();
        PittsburghPanthers pp = new PittsburghPanthers();
        assertTrue(boss.getHealth() > dd.getHealth());
        assertTrue(boss.getHealth() > be.getHealth());
        assertTrue(boss.getHealth() > pp.getHealth());
    }

    /*
    M6 - Evan Leleux
    Tests to make sure that kill count increases after an enemy death
     */
    @Test
    public void testIncreaseKillCount() {
        GameMapController gmp = new GameMapController();
        assertEquals(0, gmp.getKills());
        gmp.increaseEnemyKillCount();
        assertEquals(1, gmp.getKills());

    }

    /*
    M6 - Evan Leleux
    Tests to make sure that buzzTower's correctly set their shot boolean after each shot
     */
    @Test
    public void testShootOnce() {
        BuzzTower bt = new BuzzTower();
        TowerHandler th = new TowerHandler(bt, new Circle(100), new Pane());
        assertFalse(th.isShotOnce());
        bt.attackEnemy(th, new BostonEagle());
        assertTrue(th.isShotOnce());
    }


    /*
    M5 - Erich Drawdy
    Tests to make sure money changes after an enemy death.
     */
    @Test
    public void testGainMoneyAfterDeath() {
        Enemy[] em = new Enemy[]{new BostonEagle(), new PittsburghPanthers(), new DukeDevil()};
        for (Enemy e : em) {
            int initial = currentGame.getMoney();
            e.takeDamage(1000);
            currentGame.addMoney(e.getReward());
            int finalM = currentGame.getMoney();
            assertNotEquals(initial, finalM);

        }
    }

    /*
    M5 - Erich Drawdy
    Tests to make sure that each enemy has the correct speed.
     */
    @Test
    public void testCorrectAndDifferentSpeeds() {
        Enemy[] em = new Enemy[]{new GeorgiaBullDog(), new PittsburghPanthers(),
            new DukeDevil(), new BostonEagle()};
        assertEquals(em[0].getSpeed(), 0.5f, 0.001);
        assertEquals(em[1].getSpeed(), 0.5f, 0.001);
        assertEquals(em[2].getSpeed(), 0.3f, 0.001);
        assertEquals(em[3].getSpeed(), 0.7f, 0.001);

    }

    /*
    M5 - Braden Dunaway
    Test the upgrade cost changes as the levels of the towers increase
     */
    @Test
    public void testTowerUpgradeCost() {
        BuzzTower bt = new BuzzTower();
        ElectricTower et = new ElectricTower();
        WreckTower wt = new WreckTower();
        assertEquals(40, bt.getUpgradeCost());
        assertEquals(80, et.getUpgradeCost());
        assertEquals(200, wt.getUpgradeCost());
        bt.setLevel(10);
        et.setLevel(15);
        wt.setLevel(20);
        assertEquals(400, bt.getUpgradeCost());
        assertEquals(1200, et.getUpgradeCost());
        assertEquals(4000, wt.getUpgradeCost());

    }

    /*
    M5 - Braden Dunaway
    Tests to see if the stats are reliably upgraded for each tower,
    upgraded twice for good measure.
     */
    @Test
    public void testUpgradeBoostsStats() {
        ElectricTower et = new ElectricTower();
        TowerHandler thet = new TowerHandler(et, new Circle(100), new Pane());
        et.upgrade(thet);
        assertEquals(120, thet.getRadi().getRadius(), 0.001);
        assertEquals(4, et.getStrength());
        et.upgrade(thet);
        assertEquals(140, thet.getRadi().getRadius(), 0.001);
        assertEquals(8, et.getStrength());

        BuzzTower bt = new BuzzTower();
        TowerHandler thbt = new TowerHandler(bt, new Circle(100), new Pane());
        bt.upgrade(thbt);
        assertEquals(120, thbt.getRadi().getRadius(), 0.001);
        assertEquals(10, bt.getStrength());
        bt.upgrade(thbt);
        assertEquals(140, thbt.getRadi().getRadius(), 0.001);
        assertEquals(15, bt.getStrength());

        WreckTower wt = new WreckTower();
        TowerHandler thwt = new TowerHandler(wt, new Circle(100), new Pane());
        wt.upgrade(thwt);
        assertEquals(130, thwt.getRadi().getRadius(), 0.001);
        assertEquals(35, wt.getStrength());
        wt.upgrade(thwt);
        assertEquals(160, thwt.getRadi().getRadius(), 0.001);
        assertEquals(45, wt.getStrength());

    }

    /*
    M5 - Andrew Fitton
    Tests to make sure enemies are still visible before they take damage
     */
    @Test
    public void testForEnemyVisibility() {
        Enemy[] em = new Enemy[]{new BostonEagle(), new PittsburghPanthers(), new DukeDevil()};
        for (Enemy e : em) {
            e.takeDamage(0);
            assertTrue(e.getImage().isVisible());
        }
    }

    /*
    M5 - Andrew Fitton
    Tests to make sure enemies do damage to the monument
     */
    @Test
    public void testForDamageToMonument() {
        Enemy[] em = new Enemy[]{new BostonEagle(), new PittsburghPanthers(), new DukeDevil()};
        for (Enemy e : em) {
            int initial = currentGame.getHealth();
            currentGame.damageHealth(e);
            int finalH = currentGame.getHealth();
            assertNotEquals(initial, finalH);
        }
    }

    /*
    M5 - Evan Leleux
    Tests to make sure towers set their shot once boolean once they shoot (for shooting towers)
     */
    @Test
    public void testShotForOnce() {
        Tower[] bt = new Tower[]{new BuzzTower(), new WreckTower()};
        for (Tower t : bt) {
            TowerHandler th = new TowerHandler(t, new Circle(100), new Pane());
            t.attackEnemy(th, new BostonEagle());
            assertTrue(th.isShotOnce());
        }
    }

    /*
    M5 - Evan Leleux
    Tests to make sure enemys set their image to false once health is less than zero
     */
    @Test
    public void testEnemyImages() {
        Enemy[] em = new Enemy[]{new BostonEagle(), new PittsburghPanthers(), new DukeDevil()};
        for (Enemy e : em) {
            e.takeDamage(1000);
            assertFalse(e.getImage().isVisible());
        }
    }


    /*
    M5 - Sonya Yuen
    Tests to make sure each tower is set to the correct name variable
     */
    @Test
    public void testCorrectTowerNames() {

        Tower testBT = new BuzzTower();
        Tower testWT = new WreckTower();
        Tower testET = new ElectricTower();

        assertEquals("Buzz Shooter", testBT.getName());
        assertEquals("Ramblin Wreck", testWT.getName());
        assertEquals("Electric Tower", testET.getName());
    }

    /*
    M5 - Sonya Yuen
    Tests to make sure enemy's health decreases after being hurt by tower
     */
    @Test
    public void testEnemyTakeDamageFromTower() {

        Tower testTower = new BuzzTower();
        Enemy testEnemy = new DukeDevil();

        TowerHandler towHandler = new TowerHandler(testTower, new Circle(100), new Pane());

        int startHealth = testEnemy.getHealth();
        testTower.attackEnemy(towHandler, testEnemy);

        assertNotEquals(startHealth, testEnemy.getHealth());

    }

    /*
    M4 - Evan Leleux
    Tests to make sure initialized enemy correctly set their stats after being
    cast to their parent class
     */
    @Test
    public void testConsistentEnemyValues() {
        GeorgiaBullDog gbd = new GeorgiaBullDog(100, 0.5f, 10, 1000);
        assertEquals(gbd.getHealth(), ((Enemy) gbd).getHealth());
        assertEquals(gbd.getSpeed(), ((Enemy) gbd).getSpeed(), 1);
        assertEquals(gbd.getStrength(), ((Enemy) gbd).getStrength());
        assertEquals(gbd.getImage(), ((Enemy) gbd).getImage());
    }

    /*
    M4 - Evan Leleux
    Tests to make sure the enemy handler returns a new and different
    object than the one held in possible enemies
     */
    @Test
    public void testEnemyHandlerImages() {
        EnemyHandler eh = new EnemyHandler(1);
        for (Enemy enemy : eh.getPossibleEnemies()) {
            assertNotSame(eh.getCurrentEnemies().get(0), enemy);
        }
    }

    /*
    M4 - Andrew Fitton
    Tests to make sure different enemies have different speeds on the map
     */
    @Test
    public void testDifferentEnemySpeeds() {
        DukeDevil dbd = new DukeDevil();
        PittsburghPanthers pp = new PittsburghPanthers();
        BostonEagle bce = new BostonEagle();
        assertNotEquals(dbd.getSpeed(), pp.getSpeed());
        assertNotEquals(dbd.getSpeed(), bce.getSpeed());
        assertNotEquals(pp.getSpeed(), bce.getSpeed());
    }

    /*
    M4 - Andrew Fitton
    Tests to make sure different enemies have different healths in the game
     */
    @Test
    public void testDifferentEnemyHealth() {
        DukeDevil dbd = new DukeDevil();
        PittsburghPanthers pp = new PittsburghPanthers();
        BostonEagle bce = new BostonEagle();
        assertNotEquals(dbd.getHealth(), pp.getHealth());
        assertNotEquals(dbd.getHealth(), bce.getHealth());
        assertNotEquals(pp.getHealth(), bce.getHealth());
    }

    /*
    M4 - Sonya Yuen
    Tests to make sure different enemies have different award amounts
     */
    @Test
    public void testDifferentEnemyRewards() {
        DukeDevil dd = new DukeDevil();
        PittsburghPanthers pp = new PittsburghPanthers();
        BostonEagle be = new BostonEagle();
        GeorgiaBullDog gbd = new GeorgiaBullDog();

        assertNotEquals(dd.getReward(), pp.getReward());
        assertNotEquals(pp.getReward(), be.getReward());
        assertNotEquals(be.getReward(), gbd.getReward());
        assertNotEquals(gbd.getReward(), dd.getReward());
    }

    /*
    M4 - Sonya Yuen
    Tests to make sure different enemies have correct set strengths
     */
    @Test
    public void testDifferentEnemyStrength() {
        DukeDevil dd = new DukeDevil();
        PittsburghPanthers pp = new PittsburghPanthers();
        BostonEagle be = new BostonEagle();
        GeorgiaBullDog gbd = new GeorgiaBullDog();

        assertEquals(dd.getStrength(), 10);
        assertEquals(pp.getStrength(), 4);
        assertEquals(be.getStrength(), 1);
        assertEquals(gbd.getStrength(), 50);
    }

    /*
    M4 - Erich Drawdy
    Tests to make sure take damage works, and that isAlive switches to false
    after enemy health is less than or equal to 0.
     */
    @Test
    public void testTakeDamage() {
        GeorgiaBullDog uga = new GeorgiaBullDog();
        assertTrue(uga.isAlive());
        uga.takeDamage(100);
        assertTrue(uga.isAlive());
        assertEquals(900, uga.getHealth());
        uga.takeDamage(900);
        assertFalse(uga.isAlive());
    }

    /*
    M4 - Erich Drawdy
    Tests to make sure weight is set to the corrrect double.
    */
    @Test
    public void testDifferentWeight() {
        DukeDevil dd = new DukeDevil();
        PittsburghPanthers pp = new PittsburghPanthers();
        BostonEagle be = new BostonEagle();
        GeorgiaBullDog gbd = new GeorgiaBullDog();

        assertEquals(dd.getWeight(), .2, .01);
        assertEquals(pp.getWeight(), .5, .01);
        assertEquals(be.getWeight(), .3, .01);
        assertEquals(gbd.getWeight(), 0, .01);

    }

    /*
    M3 - Evan Leleux
    Tests to make sure each difficulty yields a different cost for Tower 1-3
     */
    @Test
    public void testDiffTowerCostDifficulty() {
        BDDGame otherGame = new BDDGame();
        currentGame.setDifficulty(1);       //Easy vs Medium
        otherGame.setDifficulty(2);
        assertNotEquals(currentGame.getTower1Cost(), otherGame.getTower1Cost());
        assertNotEquals(currentGame.getTower2Cost(), otherGame.getTower2Cost());
        assertNotEquals(currentGame.getTower3Cost(), otherGame.getTower3Cost());
        otherGame.setDifficulty(3);         //Easy vs Hard
        assertNotEquals(currentGame.getTower1Cost(), otherGame.getTower1Cost());
        assertNotEquals(currentGame.getTower2Cost(), otherGame.getTower2Cost());
        assertNotEquals(currentGame.getTower3Cost(), otherGame.getTower3Cost());
        currentGame.setDifficulty(2);       //Medium vs Hard
        assertNotEquals(currentGame.getTower1Cost(), otherGame.getTower1Cost());
        assertNotEquals(currentGame.getTower2Cost(), otherGame.getTower2Cost());
        assertNotEquals(currentGame.getTower3Cost(), otherGame.getTower3Cost());
    }

    /*
    M3 - Evan Leleux
    Tests to make sure player can't buy tower with insufficient money
     */
    @Test
    public void testBuyingTowerWithInsufficientMoney() {
        currentGame.setMoney(1);
        assertFalse(currentGame.buyTower1());
        assertFalse(currentGame.buyTower2());
        assertFalse(currentGame.buyTower3());
    }


    /*
    M3 - Andrew Fitton
    Tests to make sure that the health for the player is different for each difficulty.
     */
    @Test
    public void testHealthDifferentForDifficulties() {
        BDDGame otherGame = new BDDGame();
        currentGame.setDifficulty(1);       //Easy vs Medium
        otherGame.setDifficulty(2);
        assertNotEquals(currentGame.getHealth(), otherGame.getHealth());
        otherGame.setDifficulty(3);         //Easy vs Hard
        assertNotEquals(currentGame.getHealth(), otherGame.getHealth());
        currentGame.setDifficulty(2);       //Medium vs Hard
        assertNotEquals(currentGame.getHealth(), otherGame.getHealth());
    }

    /*
    M3 - Andrew Fitton
    Tests to make sure that the money for the player is different for each difficulty.
     */
    @Test
    public void testMoneyDifferentForDifficulties() {
        BDDGame otherGame = new BDDGame();
        currentGame.setDifficulty(1);       //Easy vs Medium
        otherGame.setDifficulty(2);
        assertNotEquals(currentGame.getMoney(), otherGame.getMoney());
        otherGame.setDifficulty(3);         //Easy vs Hard
        assertNotEquals(currentGame.getMoney(), otherGame.getMoney());
        currentGame.setDifficulty(2);       //Medium vs Hard
        assertNotEquals(currentGame.getMoney(), otherGame.getMoney());
    }


    /*
    M3- Sonya Yuen
    Tests to make sure players money is updated when tower is bought
     */
    @Test
    public void testUpdateMoneyWhenTowerBought() {
        currentGame.setDifficulty(1); //level 1 init money
        int startMoney1 = 1000;
        currentGame.buyTower1();
        currentGame.buyTower2();
        currentGame.buyTower3();
        assertEquals(currentGame.getMoney(), startMoney1 - (currentGame.getTower1Cost()
                + currentGame.getTower2Cost()
                + currentGame.getTower3Cost()));
        currentGame.setDifficulty(2);
        int startMoney2 = 500; //level 2 init money
        currentGame.buyTower1();
        currentGame.buyTower2();
        assertEquals(currentGame.getMoney(), startMoney2 - (currentGame.getTower1Cost()
                + currentGame.getTower2Cost()));

        currentGame.setDifficulty(3);
        int startMoney3 = 250; //level 3 init money
        currentGame.buyTower1();
        assertEquals(currentGame.getMoney(), startMoney3 - currentGame.getTower1Cost());
    }

    /*
    M3- Sonya Yuen
    Tests to make each level has correct starting money
     */
    @Test
    public void testCorrectStartingMoney() {
        currentGame.setDifficulty(1);
        assertEquals(1000, currentGame.getMoney());
        currentGame.setDifficulty(2);
        assertEquals(500, currentGame.getMoney());
        currentGame.setDifficulty(3);
        assertEquals(250, currentGame.getMoney());
    }

    /*
    M3 - Erich Drawdy
    Tests to make sure each level has correct starting health.
     */
    @Test
    public void testCorrectStartingHealth() {
        currentGame.setDifficulty(1);
        assertEquals(200, currentGame.getHealth());
        currentGame.setDifficulty(2);
        assertEquals(100, currentGame.getHealth());
        currentGame.setDifficulty(3);
        assertEquals(50, currentGame.getHealth());
    }

    /*
    M3 - Erich Drawdy
    Tests to make sure cost of towers in difficulty 2 is 1.5x cost in difficulty 1,
    and that cost of towers in difficulty > 2 is 2x the cost in difficulty 1.
     */
    @Test
    public void testCostIntervals() {
        BDDGame otherGame = new BDDGame();
        BDDGame newGame = new BDDGame();
        currentGame.setDifficulty(1);
        otherGame.setDifficulty(2);
        newGame.setDifficulty(3);
        assertEquals(50, otherGame.getTower1Cost() - currentGame.getTower1Cost());
        assertEquals(100, otherGame.getTower2Cost() - currentGame.getTower2Cost());
        assertEquals(250, otherGame.getTower3Cost() - currentGame.getTower3Cost());
        assertEquals(100, newGame.getTower1Cost() - currentGame.getTower1Cost());
        assertEquals(200, newGame.getTower2Cost() - currentGame.getTower2Cost());
        assertEquals(500, newGame.getTower3Cost() - currentGame.getTower3Cost());
    }

    /*
    M3 - Braden Dunaway
    Tests to make sure that the health percentage is calculating the correct float.
     */
    @Test
    public void testCorrectHealthPercentage() {
        currentGame.setDifficulty(1);
        currentGame.setHealth(100);
        assertEquals(0.50, currentGame.getHealthPercent(), 0);
        currentGame.setHealth(50);
        assertEquals(0.25, currentGame.getHealthPercent(), 0);
        currentGame.setDifficulty(2);
        currentGame.setHealth(50);
        assertEquals(0.50, currentGame.getHealthPercent(), 0);
        currentGame.setHealth(25);
        assertEquals(0.25, currentGame.getHealthPercent(), 0);
    }

    /*
    M3 - Braden Dunaway
    Tests to make sure that the difficulties are corresponding to the correct strings.
     */
    @Test
    public void testDifficultyToString() {
        currentGame.setDifficulty(0);
        assertEquals("Not Set", currentGame.difficultyToString());
        currentGame.setDifficulty(1);
        assertEquals("Easy", currentGame.difficultyToString());
        currentGame.setDifficulty(2);
        assertEquals("Medium", currentGame.difficultyToString());
        currentGame.setDifficulty(3);
        assertEquals("Hard", currentGame.difficultyToString());
        currentGame.setDifficulty(6);
        assertEquals("Hard", currentGame.difficultyToString());
    }

}
