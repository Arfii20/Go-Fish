package base.gofish;

import base.gofish.deck.Deck;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private Random random;
    private Deck deck;
    private boolean started;
    private List<Player> players;
    private int totalPoints;
    private Player winner;



    private GameEngine(){
        // Left empty intentionally
    }

    public GameEngine(long seed) {
        this.players = new ArrayList<>();
        this.random = new Random(seed);
        this.deck = new Deck();
        this.deck.shuffle(random);
        this.totalPoints = 20;
    }

    public void saveState(String path) {
        FileOutputStream outFile = null;
        ObjectOutputStream outStream = null;
        try {
            outFile = new FileOutputStream(path);
            outStream = new ObjectOutputStream(outFile);

            outStream.writeObject(this);

        } catch (IOException e) {
            System.out.println("Failed to save game state");
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
                if (outFile != null) {
                    outFile.close();
                }
            } catch (IOException e) {
                System.out.println("Error while cleaning up");
            }
        }
    }

    public static GameEngine loadState(String path) {
        GameEngine loadGame = new GameEngine();
        FileInputStream inFile = null;
        ObjectInputStream inStream = null;
        try {
            inFile = new FileInputStream(path);
            inStream = new ObjectInputStream(inFile);

            loadGame =  (GameEngine) inStream.readObject();
        } catch (IOException e) {
            System.out.println("Failed to load game state");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (inFile != null) {
                    inFile.close();
                }
            } catch (IOException e) {
                System.out.println("Error while cleaning up");
            }
        }
        return loadGame;
    }

    public Deck getGameDeck(){
        return deck;
    }

    public boolean isStarted(){
        return this.started;
    }

    public boolean gameOver() {
        for (Player player: players) {
            if (player.getPoints() > this.totalPoints) {
                this.winner = player;
            }
        }
        return false;
    }
}
