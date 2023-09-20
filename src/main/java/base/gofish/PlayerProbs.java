package base.gofish;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the probabilities associated with players having specific card values in a Go Fish game.
 * <p>
 * This class manages and updates the probabilities of each player having a specific card value.
 * It provides methods to manipulate and retrieve these probabilities during the game.
 * </p>
 * @since 1.0
 * @version 1.0
 */
public class PlayerProbs implements Serializable {
    private final List<Player> playerList;
    private final Map<Integer, Map<Player, Double>> numberProbabilities;

    @Serial
    private static final long serialVersionUID = 2L;


    /**
     * Constructs a PlayerProbs object with the specified list of players.
     *
     * @param playersIn The list of players in the game.
     */
    public PlayerProbs(List<Player> playersIn) {
        this.playerList = playersIn;
        this.numberProbabilities = new HashMap<>();
        addProbForEachNumber();
    }


    /**
     * Initializes the probabilities for each player having a specific card value.
     *
     * @return A map of player probabilities with initial values.
     */
    public Map<Player, Double> makePlayerProbabilities() {
        Map<Player, Double> probabilities = new HashMap<>();
        for (Player player : playerList) {
            probabilities.put(player, 0.25);
        }
        return probabilities;
    }


    /**
     * Adds initial probabilities for each card value.
     */
    public void addProbForEachNumber() {
        for (int i = 2; i <= 14; i++) {
            this.numberProbabilities.put(i, makePlayerProbabilities());
        }
    }


    /**
     * Updates the probabilities for a specific card value to 1.0 for a given player.
     *
     * @param value  The card value to update probabilities for.
     * @param player The player to update probabilities for.
     */
    public void updateProbabilitiesToOne(Integer value, Player player) {
        this.numberProbabilities.get(value).put(player, 1.0);
    }


    /**
     * Resets all player probabilities to their initial values (0.25).
     */
    public void resetProbabilities() {
        for (Map<Player, Double> playerProbabilities : numberProbabilities.values()) {
            playerProbabilities.replaceAll((p, v) -> 0.25);
        }
    }


    /**
     * Retrieves a player who is likely to have a specific card value.
     * <p>
     * This method selects a player from the list based on the highest probability of having
     * the specified card value. It updates the probability for that player to 0.25 to
     * avoid selecting the same player repeatedly.
     * </p>
     * @param cardValue The card value to find a player for.
     * @param curr      The current player making the request.
     * @return A player who is likely to have the specified card value, or null if no player meets the criteria.
     */
    public Player getPlayer(Integer cardValue, Player curr) {
        for (Map.Entry<Player, Double> entry : this.numberProbabilities.get(cardValue).entrySet()) {
            if ((entry.getKey() != curr) && Objects.equals(1.0, entry.getValue())) {
                entry.setValue(0.25);
                return entry.getKey();
            }
        }
        return null;
    }


    /**
     * Retrieves a player who is not likely to have a specific card value.
     * <p>
     * This method selects a player from the list who has a probability of less than 1.0
     * of having the specified card value. It selects a player randomly among those
     * who meet this criteria.
     * </p>
     * @param cardValue The card value to find a player for.
     * @param curr      The current player making the request.
     * @return A player who is not likely to have the specified card value, or null if no player meets the criteria.
     */
    public Player getPlayerFalse(Integer cardValue, Player curr) {
        List<Player> keys = new ArrayList<>();

        for (Map.Entry<Player, Double> entry : this.numberProbabilities.get(cardValue).entrySet()) {
            if ((entry.getKey() != curr) && !Objects.equals(1.0, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }

        if (keys.size() != 0) {
            Random random = new Random();
            int randint = random.nextInt(0, keys.size());
            return keys.get(randint);
        }
        else {
            return null;
        }
    }


    /**
     * Retrieves a random player different from the current player and who has cards.
     * <p>
     * This method randomly selects a player from the list who is different from the
     * current player and has cards in their hand.
     * </p>
     * @param curr The current player making the request.
     * @return A random player who is different from the current player and has cards,
     *         or null if no such player exists.
     */
    public Player getRandomPlayer(Player curr) {
        Random random = new Random();
        Player player;
        for (Player value : playerList) {
            System.out.println(value);
        }
        do {
            player = playerList.get(random.nextInt(0, playerList.size()));
        } while (player == curr || player.totalCards() == 0);
        return player;
    }
}
