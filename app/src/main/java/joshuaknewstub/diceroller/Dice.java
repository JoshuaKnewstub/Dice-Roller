package joshuaknewstub.diceroller;

import java.util.Random;

public class Dice {


    final int Sides;
    int numOfDice;

    public static final Dice[] dice = {
            new Dice(4),
            new Dice(6),
            new Dice(8),
            new Dice(10),
            new Dice(12),
            new Dice(20)
    };

    private Dice(int sides) {
        this.Sides = sides;
        this.numOfDice = 1;
    }

    public static int getIndex(int sides) {
        for (int i = 0; i < Dice.dice.length; i++) {
            if (Dice.dice[i].Sides == sides) {
                return i;
            }
        }
        return 1;
    }

    public static Integer[] getSidesAsInteger() {
        Integer[] sides = new Integer[dice.length];
        for (int i = 0; i < dice.length; i++) {
            sides[i] = dice[i].Sides;
        }
        return sides;
    }


    public int roll() {
        Random random = new Random();
        return (random.nextInt(Sides) + 1);
    }
}
