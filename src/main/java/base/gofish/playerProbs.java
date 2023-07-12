package base.gofish;

import java.util.*;

public class playerProbs {
    private final List<Player> playerList;
    private Map<Integer, Map<Player, Double>> numberProbabilities;
    public playerProbs(List<Player> playersIn) {
        this.playerList = playersIn;
    }

    public Map<Integer, Map<Player, Double>> numberProbabilities() {
        return numberProbabilities;
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

    public Player randomPlayer(Player curr) {
        Random random = new Random();
        int randint = random.nextInt(0, playerList.size());
        Player player;
        do {
            player = playerList.get(randint);
        } while (player == curr);
        return player;
    }
}
