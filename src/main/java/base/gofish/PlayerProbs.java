package base.gofish;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Random;

public class PlayerProbs implements Serializable {
    private final List<Player> playerList;
    private final Map<Integer, Map<Player, Double>> numberProbabilities;
    @Serial
    private static final long serialVersionUID = 2L;

    public PlayerProbs(List<Player> playersIn) {
        this.playerList = playersIn;
        this.numberProbabilities = new HashMap<>();
        addProbForEachNumber();
    }

    public Map<Player, Double> makePlayerProbabilities() {
        Map<Player, Double> probabilities = new HashMap<>();
        for (Player player : playerList) {
            probabilities.put(player, 0.25);
        }
        return probabilities;
    }

    public void addProbForEachNumber() {
        for (int i = 2; i <= 14; i++) {
            this.numberProbabilities.put(i, makePlayerProbabilities());
        }
    }

    public void updateProbabilitiesToOne(Integer value, Player player) {
        this.numberProbabilities.get(value).put(player, 1.0);
    }

    public void resetProbabilities() {
        for (Map<Player, Double> playerProbabilities : numberProbabilities.values()) {
            playerProbabilities.replaceAll((p, v) -> 0.25);
        }
    }

    public Player getPlayer(Integer cardValue, Player curr) {
        for (Map.Entry<Player, Double> entry : this.numberProbabilities.get(cardValue).entrySet()) {
            if ((entry.getKey() != curr) && Objects.equals(1.0, entry.getValue())) {
                entry.setValue(0.25);
                return entry.getKey();
            }
        }
        return null;
    }

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
