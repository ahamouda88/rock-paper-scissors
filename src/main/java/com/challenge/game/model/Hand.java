package com.challenge.game.model;

import java.util.Random;

public enum Hand {

    ROCK, PAPER, SCISSORS;

    private static final Random RANDOM = new Random();
    
    public static Hand getRandomHand() {
        int handIndex = RANDOM.nextInt(Hand.values().length);
        return Hand.values()[handIndex];
    }
}
