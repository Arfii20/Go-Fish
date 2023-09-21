package base.gofish.deck;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a playing card with a suit, name, and value.
 * <p>
 * This class is used to model individual playing cards, each having a suit, a name, and a numerical value.
 * Playing cards can be compared based on their values.
 *
 * @author Arefin Ahammed
 * @version 1.0
 */
public class Card implements Comparable<Card>, Serializable {
    private final String fullName;
    private final Suit suit;
    private final Name name;
    private final int value;

    @Serial
    private static final long serialVersionUID = 3L;


    /**
     * Creates a new playing card.
     *
     * @param fullName The full name of the card (e.g., "SPADES_ACE").
     * @param suit     The suit of the card.
     * @param name     The name or rank of the card.
     * @param value    The numerical value of the card.
     */
    public Card(String fullName, Suit suit, Name name, int value) {
        this.fullName = fullName;
        this.suit = suit;
        this.name = name;
        this.value = value;
    }


    /**
     * Gets the suit of the card.
     *
     * @return The suit of the card.
     */
    public Suit getType() {
        return this.suit;
    }


    /**
     * Gets the numerical value of the card.
     *
     * @return The numerical value of the card.
     */
    public int getValue() {
        return this.value;
    }


    /**
     * Gets the name or rank of the card.
     *
     * @return The name or rank of the card.
     */
    public Name getName() {
        return this.name;
    }


    /**
     * Gets the full name of the card.
     *
     * @return The full name of the card.
     */
    public String getFullName() {
        return this.fullName;
    }


    /**
     * Compares this card to another card based on their values.
     *
     * @param cardIn The card to compare to.
     * @return A negative integer if this card's value is less than the other card's value,
     *         zero if the values are equal, and a positive integer if this card's value is greater.
     */
    @Override
    public int compareTo(Card cardIn) {
        return Integer.compare(cardIn.getValue(), this.getValue());
    }
}
