package com.example.diceroller;

import java.util.Arrays;
import java.util.Random;

public class Dice {

    int numOfDice;
    Integer[] dice = new Integer[]{4, 6, 8, 10, 12, 20};
    int sides;
    Random random = new Random();

    public Dice(){
        this.sides = dice[2];
        this.numOfDice = 2;

    }

    public Dice(int numOfDice, int sides){
        this.numOfDice = numOfDice;

        if (Arrays.asList(dice).contains(sides)){
            this.sides = sides;
        } else {
            this.sides = dice[2];
        }
    }

    public int roll(){
        return random.nextInt(sides) + 1;
    }

}
