package base.gofish;

import base.gofish.deck.Card;
import base.gofish.deck.Deck;
import javafx.util.Pair;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Manages the game logic and state for the game.
 * This class handles player management, card decks, and game progression.
 *
 * @author Arefin Ahammed
 * @version 1.7
 */
public class GameEngine implements Serializable {
    private final Random random;
    private final Deck deck;
    private boolean started;
    private boolean roundOn;
    private final List<Player> players;
    private Player currentPlayer;
    private final Map<String, Player> playerMap;
    private Player realPlayer;
    private PlayerProbs playerProbabilities;
    private int difficulty;

    @Serial
    private static final long serialVersionUID = 0L;


    /**
     * Creates a new game engine with the specified random seed.
     *
     * @param seed The seed value for initializing the random number generator.
     */
    public GameEngine(long seed) {
        this.playerMap = new HashMap<>();
        this.players = new ArrayList<>();
        this.random = new Random(seed);
        this.deck = new Deck();
        this.deck.shuffle(random);
        this.deck.shuffle(random);
        this.difficulty = 2;
    }


    /**
     * Adds players to the game, including the real player.
     * Also adds the players to the playermap
     * Also makes a probability list
     *
     * @param name The name of the real player.
     */
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


    /**
     * Retrieves the player probabilities manager for the game.
     *
     * @return The player probabilities manager.
     */
    public PlayerProbs getPlayerProbabilities() {
        return playerProbabilities;
    }

//    public void setMaxPoints(int points) {
//        this.maxPoints = points;
//    }

//    public int getMaxPoints() {
//        return this.maxPoints;
//    }


    /**
     * Set difficulty.
     *
     * @param diff the diff
     */
    public void setDifficulty(int diff){
        this.difficulty = diff;
    }


    /**
     * Gets difficulty.
     *
     * @return the difficulty
     */
    public int getDifficulty() {
        return this.difficulty;
    }


    /**
     * Saves the current game state to a file at the specified path.
     *
     * @param path The file path where the game state will be saved.
     */
    public void saveState(String path) {
        try (FileOutputStream outFile = new FileOutputStream(path);
             ObjectOutputStream outStream = new ObjectOutputStream(outFile)) {

            outStream.writeObject(this);

            System.out.println("Game state saved successfully");

        } catch (IOException e) {
            System.err.println("Failed to save game state: " + e.getMessage());
        }
    }


    /**
     * Loads a saved game state from a file at the specified path.
     *
     * @param path The file path from which to load the game state.
     * @return A GameEngine instance representing the loaded game state, or null if loading fails.
     */
    public static GameEngine loadState(String path) {
        try (FileInputStream inFile = new FileInputStream(path);
             ObjectInputStream inStream = new ObjectInputStream(inFile)) {

            return (GameEngine) inStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load game state: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get deck deck.
     *
     * @return the deck
     */
    public Deck getDeck(){
        return deck;
    }


    /**
     * Set started.
     *
     * @param start the start
     */
    public void setStarted(boolean start){
        this.started = start;
    }


    /**
     * Set round on.
     *
     * @param roundOn the round on
     */
    public void setRoundOn(boolean roundOn){
        this.roundOn = roundOn;
    }


    /**
     * Is started boolean.
     *
     * @return the boolean
     */
    public boolean isStarted(){
        return this.started;
    }


    /**
     * Is round on boolean.
     *
     * @return the boolean
     */
    public boolean isRoundOn(){
        return this.roundOn;
    }


    /**
     * Starts a new round by shuffling the deck and dealing cards to all players.
     * Each player is dealt 5 cards.
     * This method sets the round status to "in-progress."
     */
    public void roundStart() {
        deck.shuffle(this.random);
        for (int i = 0; i < 5; i++) {
            for (Player player : players) {
                player.addToHand(deck.draw());
            }
        }
        this.roundOn = true;
    }


    /**
     * Checks if the current round is over.
     *
     * @return {@code true} if all players have cleared their hands or the deck is empty, indicating the round is over; {@code false} otherwise.
     */
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


    /**
     * Starts a new turn for the next player in the turn order.
     * If there's no current player, it initializes the first player's turn.
     */
    public void startTurn() {
        if (currentPlayer == null) {
            this.currentPlayer = this.players.remove(0);
        }
    }


    /**
     * Ends the current player's turn and moves them to the end of the turn order.
     */
    public void endTurn() {
        this.players.add(this.currentPlayer);
        this.currentPlayer = null;
    }


    /**
     * Executes a single turn in the game where a player asks another player for a specific card.
     * If the target player has the requested card, it is removed from their hand and returned.
     * If not, returns null.
     *
     * @param playerIn The name of the player making the request.
     * @param cardIn   The name of the card being requested.
     * @return A list of cards received from the target player, or null if the request was unsuccessful.
     */
    public List<Card> singleTurn(String playerIn, String cardIn) {
        Player player = this.playerMap.get(playerIn);
        Card card = this.deck.getCardMap().get(cardIn);

        System.out.println(player + ": " + card.getValue());

        if (player.hasCard(card)){
            return player.clearCardFromHand(card.getValue());
        }
        return null;
    }


    /**
     * Determines the player and card to request from the current player's cards based on the game's difficulty.
     * The selection logic depends on the difficulty level:
     * - Easy (1): Randomly selects a player.
     * - Medium (2): Has a small chance of selecting a random player, otherwise selects based on probabilities.
     * - Hard (3): Selects based on probabilities.
     *
     * @return A Pair containing the selected player and card for the request.
     * @throws IllegalArgumentException If the selection logic fails or no valid player and card can be selected.
     */
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


    /**
     * Selects a player for a card request based on the game's difficulty.
     * The selection logic depends on the difficulty level:
     * - Easy (1): Randomly selects a player.
     * - Medium (2): Has a small chance of selecting a random player, otherwise selects based on probabilities.
     * - Hard (3): Selects based on probabilities.
     *
     * @param card The card for which a player is being selected.
     * @return The selected player or null if no valid player can be selected.
     */
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


    /**
     * Gets a list of players sorted by their current points in descending order. The current player, if not null, is also included.
     *
     * @return A list of players sorted by points, with the current player included if present.
     */
    public List<Player> getSortedPlayers() {
        List<Player> tempPlayers = new ArrayList<>(players);
        if (currentPlayer != null) tempPlayers.add(currentPlayer);
        Collections.sort(tempPlayers);
        return tempPlayers;
    }


    /**
     * Checks if all players have empty hands (no cards remaining).
     *
     * @return True if all players have empty hands, false otherwise.
     */
    public boolean allEmpty(){
        int count = 0;
        for (Player player: players) if (player.cardFinished()) count++;
        return count == 4;
    }


    /**
     * Gets real player.
     *
     * @return the real player
     */
    public Player getRealPlayer() {
        return this.realPlayer;
    }


    /**
     * Gets current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }


    /**
     * Determines the player with the highest points as the winner.
     *
     * @return The player with the highest points. If multiple players have the same highest points, the first encountered player with the highest points is returned.
     */
    public Player getWinner() {
        //    private int maxPoints;
        Player winner = null;
        for (Player player : players) {
            if (winner == null || player.getPoints() > winner.getPoints()) {
                winner = player;
            }
        }
        return winner;
    }

//    public void printPlayerCards() {
//        for (Player player: getSortedPlayers()) {
//            System.out.println(player + " total cards: " + player.totalCards());
//        }
//        System.out.println();
//    }
}
