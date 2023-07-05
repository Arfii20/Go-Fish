package base.gofish;

import base.gofish.deck.Deck;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine {
    private Random random;
    private Deck deck;

    private boolean hasStarted;

    private GameEngine(){
        // Left empty intentionally
    }

    public GameEngine(long seed) {
        this.random = new Random(seed);
        this.deck = new Deck();
        this.deck.shuffle(random);
    }

}


