package base.gofish;

import base.gofish.deck.Card;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Player implements Comparable<Player>, Serializable {
    private String name;
    private List<Card> cards;
    private int value;
    private int points;

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * Constructs a player with the given name and initial value.
     *
     * @param name  The name of the player.
     * @param value The initial value of the player.
     */
    public Player(String name, int value){
        this.name = name;
        this.value = value;
        this.cards = new ArrayList<>();
        this.points = 0;
    }


    /**
     * Retrieves the current points of the player.
     *
     * @return The current points of the player.
     */
    public int getPoints() {
        return points;
    }


    /**
     * Retrieves the cards held by the player in sorted order.
     *
     * @return A list of cards held by the player in sorted order.
     */
    public List<Card> getCards() {
        Collections.sort(cards);
        return cards;
    }


    /**
     * Retrieves a random card from the player's hand.
     *
     * @param random The random number generator to use for selecting the card.
     * @return A randomly selected card from the player's hand.
     * @throws IllegalArgumentException If the player's hand is empty.
     */
    public Card getRandomCard(Random random) throws IllegalArgumentException {
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("Player's hand is empty.");
        }
        return this.cards.get(random.nextInt(this.cards.size()));
    }


    /**
     * Adds a card to the player's hand and updates points if a set of four is completed.
     *
     * @param card The card to add to the player's hand.
     */
    public void addToHand(Card card) {
        this.cards.add(card);
        if (cardCount(card.getValue()) == 4) {
            points++;
            this.clearCardFromHand(card.getValue());
        }
    }


    /**
     * Adds a list of cards to the player's hand and updates points if a set of four is completed.
     *
     * @param cards The list of cards to add to the player's hand.
     */
    public void addToHand(List<Card> cards) {
        this.cards.addAll(cards);
        if (this.cardCount(cards.get(0).getValue()) == 4) {
            points++;
            this.clearCardFromHand(cards.get(0).getValue());
        }
    }


    /**
     * Clears all cards of a specified value from the player's hand.
     *
     * @param val The value of cards to clear from the player's hand.
     * @return A list of cards cleared from the player's hand.
     */
    public List<Card> clearCardFromHand(int val) {
        List<Card> tempCards = new ArrayList<>();
        Iterator<Card> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getValue() == val) {
                tempCards.add(card);
                iterator.remove();
            }
        }
        return tempCards;
    }


    /**
     * Checks if the player has a specific card.
     *
     * @param cardIn The card to check for.
     * @return {@code true} if the player has the card, {@code false} otherwise.
     */
    public boolean hasCard(Card cardIn) {
        for (Card card : this.cards) {
            if (card.getValue() == cardIn.getValue()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Counts the number of cards with a specific value in the player's hand.
     *
     * @param val The value to count.
     * @return The number of cards with the specified value in the player's hand.
     */
    public int cardCount(int val) {
        int count = 0;
        for (Card card : cards) {
            if (card.getValue() == val) {
                count++;
            }
        }
        return count;
    }


    /**
     * Retrieves the total number of cards in the player's hand.
     *
     * @return The total number of cards in the player's hand.
     */
    public int totalCards() {
        return this.cards.size();
    }


    /**
     * Checks if the player has finished all their cards.
     *
     * @return {@code true} if the player has no more cards, {@code false} otherwise.
     */
    public boolean cardFinished() {
        return (cards.size() == 0);
    }


    /**
     * Retrieves the value associated with the player.
     *
     * @return The value associated with the player.
     */
    public int getValue() {
        return this.value;
    }


    /**
     * Returns a string representation of the player, which is their name.
     *
     * @return The name of the player.
     */
    @Override
    public String toString() {
        return this.name;
    }


    /**
     * Compares this player to another player based on their points.
     *
     * @param playerIn The player to compare to.
     * @return A negative integer if this player has fewer points, a positive integer if this player has more points,
     *         or zero if they have the same number of points.
     */
    @Override
    public int compareTo(Player playerIn) {
        return Integer.compare(playerIn.getPoints(), this.getPoints());
    }

}
