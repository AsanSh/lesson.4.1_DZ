import java.util.Random;

public class Main {

    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static int[] heroesHealth = {260, 250, 240, 300};
    public static int[] heroesDamage = {20, 25, 15, 0};
    public static String[] heroesAttackType = {"Physical",
            "Magical", "Kinetic", "Medic"};

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }
    /*
    Добавить 4-го игрока Medic, у которого есть способность лечить после каждого
    раунда на N-ное количество единиц здоровья только одного из членов команды,
    имеющего здоровье менее 100 единиц. Мертвых героев медик оживлять не может,
    и лечит он до тех пор пока жив сам. Медик не участвует в бою, но получает урон от Босса
     */

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefence);
    }

    public static void round() {
        changeBossDefence();
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
        double coeff = Math.random(); // какое-то число от 0 до 1 (0.378)
        if(chance == 0) {
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
            if(heroesHealth[i] > 0){
                allHeroesDead = false;
                break;
            }
        }
        return allHeroesDead;
    }
}
