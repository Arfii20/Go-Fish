package base.gofish;

import base.gofish.deck.Card;
import base.gofish.deck.Deck;
import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class GameEngine {
    private Random random;
    private Deck deck;
    private boolean started;
    private boolean roundOn;
    private boolean turnOn;
    private List<Player> players;
    private List<Player> playersCopy;
    private Player currentPlayer;
    private Map<String, Player> playerMap;
    private int maxPoints;
    private Player winner;
    private Player realPlayer;
    private PlayerProbs playerProbabilities;
    private int difficulty;


    private GameEngine(){
        // Left empty intentionally
    }

    public GameEngine(long seed) {
        this.playerMap = new HashMap<>();
        this.players = new ArrayList<>();
        this.random = new Random(seed);
        this.deck = new Deck();
        this.deck.shuffle(random);
        this.deck.shuffle(random);
        this.difficulty = 2;
    }

    public void addPlayers(String name) {
        this.players.add(new Player(name, 1));
        this.players.add(new Player("Player2", 2));
        this.players.add(new Player("Player3", 3));
        this.players.add(new Player("Player4", 4));
        this.players.add(new Player("Player5", 5));

        this.realPlayer = players.get(0);

        for (Player player : this.players) {
            this.playerMap.put(player.toString(), player);
        }

        this.playerProbabilities = new PlayerProbs(players);
    }

    public PlayerProbs getPlayerProbabilities() {
        return playerProbabilities;
    }

    public void setMaxPoints(int points) {
        this.maxPoints = points;
    }

    public int getMaxPoints() {
        return this.maxPoints;
    }
    
    public void setDifficulty(int diff){
        this.difficulty = diff;
    }

    public int getDifficulty() {
        return this.difficulty;
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

    public Deck getDeck(){
        return deck;
    }

    public void setStarted(boolean start){
        this.started = start;
    }

    public void setRoundOn(boolean roundOn){
        this.roundOn = roundOn;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }

    public boolean isStarted(){
        return this.started;
    }

    public boolean isRoundOn(){
        return this.roundOn;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public void roundStart() {
        deck.shuffle(this.random);
        for (int i = 0; i < 5; i++) {
            for (Player player : players) {
                player.addToHand(deck.draw());
            }
        }
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
        return !(this.roundOn = !(handsClear && deck.isEmpty()));
    }

    public void startTurn() {
        this.turnOn = true;
        if (currentPlayer == null) {
            this.currentPlayer = this.players.remove(0);
        }
    }

    public void endTurn() {
        this.players.add(this.currentPlayer);
        this.currentPlayer = null;
        this.turnOn = false;
    }

    public List<Card> singleTurn(String playerIn, String cardIn) {
        Player player = this.playerMap.get(playerIn);
        Card card = this.deck.getCardMap().get(cardIn);
        if (player.hasCard(card)){
            return player.clearCardFromHand(card.getValue());
        }
        return null;
    }

    public Pair<Player, Card> getPlayerCardForBots() throws IllegalArgumentException {
        Player player;
        for (Card card : this.currentPlayer.getCards()) {
            if (this.currentPlayer.cardCount(card.getValue()) == 3) {
                player = this.getPlayerAccDifficulty(card);
                if (player != null) return new Pair<>(player, card);
            }
        }
        for (Card card : this.currentPlayer.getCards()) {
            if (this.currentPlayer.cardCount(card.getValue()) == 2) {
                player = this.getPlayerAccDifficulty(card);
                if (player != null && player.cardCount(card.getValue()) == 2) return new Pair<>(player, card);
            }
        }
        for (Card card : this.currentPlayer.getCards()) {
            if (this.currentPlayer.cardCount(card.getValue()) == 1) {
                player = this.getPlayerAccDifficulty(card);
                if (player != null) return new Pair<>(player, card);
            }
        }
        return new Pair<>(this.playerProbabilities.getRandomPlayer(this.currentPlayer), this.currentPlayer.getRandomCard(random));
    }

    private Player getPlayerAccDifficulty(Card card) {
        Player player;
        player = switch (this.difficulty) {
            case 1 -> playerProbabilities.getRandomPlayer(this.currentPlayer);
            case 2 -> random.nextInt(67, 70) == 69 ? playerProbabilities.getPlayerFalse(card.getValue(), this.currentPlayer) : playerProbabilities.getPlayer(card.getValue(), this.currentPlayer);
            case 3 -> playerProbabilities.getPlayer(card.getValue(), this.currentPlayer);
            default -> null;
        };
        return player;
    }

    public List<Player> getSortedPlayers() {
        List<Player> tempPlayers = new ArrayList<>(players);
        Collections.sort(tempPlayers);
        return tempPlayers;
    }

    public boolean allEmpty(){
        int count = 0;
        for (Player player: players) {
            if (player.cardFinished()) {
                count++;
            }
        }
        return count == 4;
    }

    public Player getRealPlayer() {
        return this.realPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
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
