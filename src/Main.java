import java.util.Random;

public class Main {

    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static int[] heroesHealth = {260, 250, 240, 300, 500, 1, 1};
    public static int[] heroesDamage = {20, 25, 15, 1, 1, 1};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Berserk", "Thor"};


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefence);
    }

    public static void round() {
        changeBossDefence();
        medicSelection();
        thor();
        heroesHit();
        if (bossHealth > 0) {
            bossHits();
        }
        printStatistics();
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random r = new Random();
                    int coeff = r.nextInt(6) + 2; //2,3,4,5,6,7,8,9
                    System.out.println("Critical Damage: " +
                            heroesDamage[i] * coeff);
                    if (heroesAttackType[4] == bossDefence) {
                        heroesHealth[i] = heroesHealth[i] + bossDamage / 5;
                    }
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        Random r = new Random();
        int chance = r.nextInt(3); // 0, 1, 2
        int coeff = r.nextInt(100); // какое-то число от 0 до 1 (0.378)
        int lucky = r.nextInt(3);
        if (lucky == 0) {
            System.out.println("он укланился от удара " + bossDamage * 0);
        }
        if (heroesAttackType[5] == bossDefence) {
            heroesHealth[5] = heroesHealth[5] + bossDamage * 2;
        }
        if (chance == 0) {
            System.out.println("Boss became kind " + (int) (bossDamage * coeff));
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (chance == 0) {
                    if (heroesHealth[i] - (int) (bossDamage * coeff) < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - (int) (bossDamage * coeff);
                    }
                } else {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }
    }

    public static void medicSelection() {

        for (int i = 0; i < heroesAttackType.length; i += 20) {
            if (heroesAttackType[3] == bossDefence) {
                if (heroesHealth[i] < 100) {
                    heroesHealth[i] = heroesHealth[i] + 99999;
                }
            }

        }
    }

    public static void thor() {
        if (heroesAttackType[6] == bossDefence) {
            bossDamage = 0;
            System.out.println("босс пропускает раунд");
        }
    }

    public static void printStatistics() {
        System.out.println("________________");
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i]
                    + " health: " + heroesHealth[i]);
        }
        System.out.println("________________");
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        return allHeroesDead;
    }
}
/*
ДЗ на сообразительность:
● Добавить n-го игрока, Golem, который имеет увеличенную жизнь но слабый удар.
Может принимать на себя 1/5 часть урона исходящего от босса по другим игрокам.!!!!!!!!!!!!!!!

● Добавить n-го игрока, Lucky, имеет шанс уклонения от ударов босса.!!!!!!!!!!!!!!


● Добавить n-го игрока, Berserk, блокирует часть удара босса по себе и прибавляет
заблокированный урон к своему урону и возвращает его боссу!!!!!!!!!!!!!!!!!!!!!

● Добавить n-го игрока, Thor, удар по боссу имеет шанс оглушить босса на 1 раунд,
вследствие чего босс пропускает 1 раунд и не наносит урон героям.!!!!!!!!!!!!!!!!!!!!

    Добавить 4-го игрока Medic, у которого есть способность лечить после каждого
    раунда на N-ное количество единиц здоровья только одного из членов команды,
    имеющего здоровье менее 100 единиц. Мертвых героев медик оживлять не может,
    и лечит он до тех пор пока жив сам. Медик не участвует в бою, но получает урон от Босса!!!!!!!!!
     */

