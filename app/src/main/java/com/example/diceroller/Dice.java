package com.example.diceroller;

import java.util.Arrays;
import java.util.Random;

public class Dice {

    public static Integer[] dice = new Integer[]{4, 6, 8, 10, 12, 20};
    int numOfDice;
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

    public int[] roll(){
        int[] results = new int[numOfDice];
        for (int i = 0; i < numOfDice; i++){
            results[i] = random.nextInt(sides) + 1;
        }
        return results;
    }

}
