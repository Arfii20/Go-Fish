package base.gofish;

import base.gofish.deck.Card;
import base.gofish.deck.Deck;

import java.io.*;
import java.util.*;

public class GameEngine {
    private Random random;
    private Deck deck;
    private boolean started;
    private boolean roundOn;
    private List<Player> players;
    private Player currentPlayer;
    private Map<String, Player> playerMap;
    private int totalPoints;
    private Player winner;


    private GameEngine(){
        // Left empty intentionally
    }

    public GameEngine(long seed) {
        this.playerMap = new HashMap<>();
        this.players = new ArrayList<>();
        this.random = new Random(seed);
        this.deck = new Deck();
        this.deck.shuffle(random);
        this.totalPoints = 1000;
    }

    public int addPlayers(String name) {
        players.add(new Player(name));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        players.add(new Player("Player4"));
        players.add(new Player("Player5"));

        for (Player player : players) {
            playerMap.put(player.toString(), player);
        }

        return players.size();
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

    public boolean isRoundOn(){
        return this.roundOn;
    }

    public void roundStart() {
        deck.shuffle(this.random);
        for (int i = 0; i < 5; i++) {
            for (Player player : players) {
                player.addToHand(deck.draw());
            }
        }
        this.currentPlayer = this.players.remove(0);
        this.roundOn = true;
    }

    public boolean roundOver() {
        boolean handsClear = true;
        for (Player player : players) {
            if (!player.cardFinished()) {
                handsClear = false;
                break;
            }
        }
        this.players.add(currentPlayer);
        return !(this.roundOn = !(handsClear && deck.isEmpty()));
    }

    public Card singleTurn(String playerIn, String cardIn) {
        Player player = playerMap.get(playerIn);
        Card card = deck.getCardMap().get(cardIn);
        Card drawnCard = null;
        if (player.hasCard(card)){
            List<Card> cards = player.clearCardFromHand(card.getValue());
            this.currentPlayer.addToHand(cards);
        }
        else {
            drawnCard = deck.draw();
            currentPlayer.addToHand(drawnCard);
        }
        return drawnCard;
    }

    public List<Player> sortedPlayers() {
        List<Player> tempPlayers = new ArrayList<>(players);
        Collections.sort(tempPlayers);
        return tempPlayers;
    }

    public Player getWinner() {
        this.winner = null;
        for (Player player : players) {
            if (this.winner == null || player.getPoints() > this.winner.getPoints()) {
                this.winner = player;
            }
        }
        return this.winner;
    }
}
