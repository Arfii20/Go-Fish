package base.gofish.deck;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * The Deck class represents a standard deck of playing cards.
 * It includes methods for creating, shuffling, drawing cards, and managing the deck's state.
 * <p>
 * Example usage:
 * <pre>
 * Deck myDeck = new Deck();
 * myDeck.shuffle(new Random());
 * Card drawnCard = myDeck.draw();
 * </pre>
 *
 * @author Arefin Ahammed
 * @version 1.0
 */
public class Deck implements Serializable {
    private final List<Card> deck = new ArrayList<>();
    private final Map<String, Card> cardMap = new HashMap<>();

    @Serial
    private static final long serialVersionUID = 4L;


    /**
     * Constructs a new deck of playing cards containing all standard cards.
     * Each card is represented as an instance of the Card class and is added to the deck.
     * The deck is initially sorted by suit and rank.
     */
    public Deck(){
        int count;
        for (Suit suit: Suit.values()) {
            count = 2;
            for (Name name: Name.values()) {
                deck.add(new Card(suit.name() + "_" + name.name(), suit, name, count++));
            }
        }

        for (Card card : deck) {
            cardMap.put(card.getFullName(), card);
        }
    }


    /**
     * Restocks the deck to its initial state by recreating all standard cards.
     */
    public void restockDeck(){
        int count;
        for (Suit suit: Suit.values()) {
            count = 2;
            for (Name name: Name.values()) {
                deck.add(new Card(suit.name() + "_" + name.name(), suit, name, count++));
            }
        }
    }


    /**
     * Draws the top card from the deck. The drawn card is removed from the deck.
     *
     * @return The card drawn from the deck, or null if the deck is empty.
     */
    public Card draw(){
        Card lastCard = null;
        if (!deck.isEmpty()) {
            lastCard = deck.remove(deck.size() - 1);
        }
        return lastCard;
    }


    /**
     * Shuffles the deck using the provided random number generator.
     *
     * @param random The random number generator used for shuffling.
     */
    public void shuffle(Random random){
        Collections.shuffle(deck, random);
    }


    /**
     * Gets the current size of the deck.
     *
     * @return The number of cards in the deck.
     */
    public int size(){
        return deck.size();
    }


    /**
     * Checks if the deck is empty.
     *
     * @return {@code true} if the deck is empty, {@code false} otherwise.
     */
    public boolean isEmpty(){
        return deck.isEmpty();
    }


    /**
     * Gets a map that allows looking up cards by their full names.
     *
     * @return A map of card full names to card objects.
     */
    public Map<String, Card> getCardMap() {
        return this.cardMap;
    }
}
